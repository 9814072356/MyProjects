from faulthandler import disable
from django.shortcuts import redirect, render
from django.contrib import messages
from django.conf import settings
from django.http import HttpResponse,HttpResponseRedirect, JsonResponse
from django.urls.base import reverse
from django.utils.http import url_has_allowed_host_and_scheme, urlsafe_base64_encode
from django.utils.encoding import force_bytes
from django.utils.translation import gettext_lazy as _
from django.contrib.auth import login, get_user_model
from django.contrib.auth.forms import PasswordResetForm, AuthenticationForm
from django.contrib.auth.decorators import login_required
from django.contrib.auth.tokens import default_token_generator
from django.contrib.auth.mixins import LoginRequiredMixin
from django.db.models.query_utils import Q
from django.template.loader import render_to_string, get_template
from django.views.generic import CreateView
from django.views.decorators.clickjacking import xframe_options_sameorigin
from django.contrib.auth import update_session_auth_hash

from xhtml2pdf import pisa
from PIL import Image
from base64 import b64encode
from io import BytesIO
from datetime import datetime
from random import randint
from typing import Any
from bs4 import BeautifulSoup
import smtplib, ssl
import nums_from_string
import re

from .models import Profile, Education, Experience, Skill, Post
from .forms import ProfileForm, SimpleUserCreationForm
from .forms import EduFormset, ExpFormset, SkillFormset
from .forms import VeriCodeForm, VCodeForm
from .forms import PostForm
from crispy_forms.helper import FormHelper
from crispy_forms.layout import Layout, Field

from requests import get as get_request, HTTPError
from django.core.files.base import ContentFile

def download_profile_image(profile):
    image = profile.image
    if image:
        try:
            response = get_request(image.name)
            response.raise_for_status()

        except:
            image.name = None
            setattr(image.instance, image.field.attname, image.name)
            image._committed = False

        else:
            name = "%s_%s" % (profile.firstName, profile.lastName)
            image.save(name, ContentFile(response.content), save=False)

def create_verification_code(n):
    range_start = 10**(n-1)
    range_end = (10**n)-1
    return randint(range_start, range_end)

def send_code(request ):
    if request.method == "POST":
        veriform = VCodeForm(request.POST, request.FILES)   # create the verification form
        if veriform.is_valid(): # check if the mandatory field has been filled
            port = 465  # the port number, in which the mail sending service runs
            sender = 'cvparserg1@gmail.com' # mail of the sender. This one must not be changed.
            receiver = veriform.cleaned_data.get("mail")    # get the user's email from the form
            vCode = create_verification_code(8) # generate the 8-digit code
            message = 'Subject: {}\n\nYour 8-digit code: {code}\n\n Have a nice day :)'.format("Verification code", code=vCode) #verification code to be sent to user
            regform = SimpleUserCreationForm()  # create the registration form
            regform.fields['email'].initial = receiver #autofill email field in the register form
            context = ssl.create_default_context()  # create context for email
            with smtplib.SMTP_SSL("smtp.gmail.com",port,context=context) as server:
                try:
                    server.login(sender, "Parser@Gr1$Wp_luh2122") # login to the sender email
                    server.sendmail(sender, receiver, message)  # server tries to send out an email to the receiver
                    messages.info(request, _("Code sent to {mail}").format(mail=receiver))     # inform the user that the mail has been sent
                    messages.info(request, _("Please do not refresh this page. Your entries will be lost on refresh."))    # inform the user not to refreesh the page
                    # initiate the registration form
                    form_helper = FormHelper()
                    form_helper.form_tag = False
                    form_helper.render_unmentioned_fields = True
                    form_helper.layout = Layout(Field("email", disabled=True))
                    return render(request, "parser_main/simpleForm.html", {"title": _("CREATE USER"), "form": regform, "veriform": VeriCodeForm(), "vc": vCode,"post_url": reverse("parser_main:register") })
                except smtplib.SMTPConnectionError: # mail sending error handler
                    print(_logger.exception('SMTP Connection failed'))
                except Exception as e: # any other errors handler
                    messages.info(request, _(str(e)))
                    return render(request, "parser_main/verifyCode.html", {"title": _("CREATE USER"), "form": VCodeForm(), "post_url": reverse("parser_main:send") })
    else:
        messages.info(request, _("Please enter your email address to proceed. For certainty please use gmail."))
        # inititate code sending form
        return render(request, "parser_main/verifyCode.html", {"title": _("CREATE USER"), "form": VCodeForm(), "post_url": reverse("parser_main:send") })

# Create your views here.
def sign_up(request):
    form, veri_code_form = None, None
    veriCode = None
    if request.method == "POST":
        # print(request.POST)
        form = SimpleUserCreationForm(request.POST)
        veri_code_form = VeriCodeForm(request.POST)

        try:
            code = int(request.POST['code'])
        except:
            code = 0
        veriCode = int(request.POST['v_code'])
        if form.is_valid():
            if code == veriCode:
                form.save()
                messages.success(request, _("New user created successfully."))
                return redirect(reverse("parser_main:login"))
            else:
                messages.info(request, _("Invalid code."))

    else:
        if request.user.is_authenticated:
            messages.info(request, _("You are already logged in."))
            return redirect(settings.LOGIN_REDIRECT_URL)
        form = SimpleUserCreationForm()
        veri_code_form = VeriCodeForm()

    form_helper = FormHelper()
    form_helper.form_tag = False
    form_helper.render_unmentioned_fields = True
    form_helper.layout = Layout(Field("email", disabled=True))

    return render(request, "parser_main/simpleForm.html", {"title": _("CREATE USER"), "form": form, "veriform": veri_code_form, "vc": veriCode,"post_url": reverse("parser_main:register") })

def get_redirect_url(request):
    """Return the user-originating redirect URL if it's safe."""
    redirect_to = request.GET.get("next", None)

    url_is_safe = False if redirect_to is None else url_has_allowed_host_and_scheme(
        url= redirect_to,
        allowed_hosts= { request.get_host() },
        require_https= request.is_secure(),
    )
    return redirect_to if url_is_safe else settings.LOGIN_REDIRECT_URL

def sign_in(request):
    if request.method == "POST":
        form = AuthenticationForm(request, request.POST)
        form.full_clean()
        user = form.get_user()

        if user is not None:
            login(request, user)
            messages.success(request, _("You are now logged in as ") + user.email)
            return redirect(get_redirect_url(request))
            
        return render(request, "parser_main/simpleForm.html", {"title": _("LOGIN"), "form": form, "post_url": request.get_full_path() })
    else:
        if request.user.is_authenticated:
            messages.info(request, _("You are already logged in."))
            return HttpResponseRedirect('/extractor/')

        return render(request, "parser_main/simpleForm.html", {"title": _("LOGIN"), "form": AuthenticationForm(), "post_url": request.get_full_path() })

@login_required
@xframe_options_sameorigin
def edit(request):
    profile, eduset, expset, skillset = None, None, None, None
    edu_open, exp_open, skill_open = False, False, False

    try:
        profile = Profile.objects.filter(user=request.user).last()
    except:
        profile = None

    if request.method == "POST":
        form = ProfileForm(request.POST, request.FILES, instance=profile)
        profile = form.save(commit=False)
        profile.user = request.user

        eduset = EduFormset(request.POST, instance=profile)
        expset = ExpFormset(request.POST, instance=profile)
        skillset = SkillFormset(request.POST, instance=profile)

        if request.POST["delete_only"] == "true":
            eduset.save_existing_objects(commit=False)
            expset.save_existing_objects(commit=False)
            skillset.save_existing_objects(commit=False)

            for obj in eduset.deleted_objects:
                obj.delete()

            for obj in expset.deleted_objects:
                obj.delete()
                
            for obj in skillset.deleted_objects:
                obj.delete()
            
            return HttpResponse("Success")

        elif form.is_valid():
            if form.has_changed():
                profile.save()
                messages.success(request, _("Profile was updated successfully"))
                
                form = ProfileForm(instance=profile)

            if eduset.is_valid() and eduset.has_changed():
                eduset.save()
                messages.success(request, _("Education was updated successfully"))
                eduset = EduFormset(instance=profile)

            elif not eduset.is_valid():
                edu_open = True

            if expset.is_valid() and expset.has_changed():
                expset.save()
                messages.success(request, _("Experience was updated successfully"))
                expset = ExpFormset(instance=profile)

            elif not expset.is_valid():
                exp_open = True

            if skillset.is_valid() and skillset.has_changed():
                skillset.save()
                messages.success(request, _("Skills updated successfully"))
                skillset = SkillFormset(instance=profile)

            elif not skillset.is_valid():
                skill_open = True
    
    else:
        form = ProfileForm(instance=profile)

        eduset = EduFormset(instance=profile)
        expset = ExpFormset(instance=profile)
        skillset = SkillFormset(instance=profile)

    try:
        if profile.image:
            image = Image.open(profile.image)
            mime_format = Image.MIME.get(image.format)

            if mime_format:
                with BytesIO() as buffer:
                    image.save(buffer, format=image.format)
                    image = "%s; base64, %s" % (mime_format, b64encode(buffer.getvalue()).decode())
            else:
                image = None
        else:
            image = None
    except:
        image = None

    return render(request, "parser_main/edit.html", {
        "post_url": reverse("parser_main:edit"),
        "form": form,
        "image": image,
        "eduset": eduset, 
        "expset": expset, 
        "skillset": skillset,
        "edu_open": edu_open,
        "exp_open": exp_open,
        "skill_open": skill_open,
        })

@login_required
def render_pdf_view(request):
    try:
        profile = Profile.objects.filter(user=request.user).last()
        eduset = Education.objects.filter(profile=profile)
        skillset= Skill.objects.filter(profile=profile)
        expset= Experience.objects.filter(profile=profile)
        template_path = 'pdf.html'
        context = {'Profile': profile, 'Eduset':eduset, 'Expset':expset, 'Skillset':skillset }
        # Create a Django response object, and specify content_type as pdf
        response = HttpResponse(content_type='application/pdf')
        response['Content-Disposition'] = 'attachment; filename="report_.pdf"'
        # find the template and render it.
        template = get_template(template_path)
        html = template.render(context)

        # create a pdf
        pisa_status = pisa.CreatePDF(html, dest=response)
        # if error then show some funy view
        if pisa_status.err:
            return HttpResponse(_('We had some errors <pre>') + html + '</pre>')
        return response
    except Exception as e:
        messages.error(request, _(str(e)))
        return HttpResponseRedirect("/edit/")

@login_required
def about(request):
    return render(request, "parser_main/about.html")

# @login_required
# def account(request):
#     RPFFA = PasswordChangeForm(user=request.user)
#     if request.method == "POST":
#         if RPFFA.is_valid():
#             form.save()
#             update_session_auth_hash(request, form.user)
#         return render(request, "parser_main/account.html", {"form": RPFFA, "post_url": request.get_full_path() })
#     else:
#         return render(request, "parser_main/account.html", {"form": RPFFA, "post_url": request.get_full_path() })

def verify(request):
    if request.method == "POST":
        password_reset_form = PasswordResetForm(request.POST)   # create password reset form
        if password_reset_form.is_valid():  # check if the form is valid
            data = password_reset_form.cleaned_data['email']    # get the account's email address from the form
            User = get_user_model() # get the user model to change the password
            associated_users = User.objects.filter(Q(email=data))   # filter out the accounts with the given email
            if associated_users.exists():   # check if those users exist
                # ---start of reset link sending proccess---
                port = 465
                sender = 'cvparserg1@gmail.com'
                context = ssl.create_default_context()
                for user in associated_users:
                    message = 'Subject: {}\n\n'.format("Reset password")
                    email_template_name = "rpText.txt"
                    c = {
                    "email":user.email,
                    'domain':'epikur.se.uni-hannover.de:8000',
                    'site_name': 'Website',
                    "uid": urlsafe_base64_encode(force_bytes(user.pk)),
                    "user": user,
                    'token': default_token_generator.make_token(user),
                    'protocol': 'http',
                    }
                    message += render_to_string(email_template_name, c)
                    with smtplib.SMTP_SSL("smtp.gmail.com",port,context=context) as server:
                        server.login(sender, "Parser@Gr1$Wp_luh2122")
                        server.sendmail(sender, [user.email], message)
                        messages.info(request, _("Reset link was sent to {mail}").format(mail=data))
                        return render(request, "parser_main/confirm.html")
                # ---end of sending proccess---      
            else:   # if the account does not exist, user will be asked to give a different email address
                messages.info(request, _("Account does not exist"))
                return render(request, "parser_main/verifyCode.html", {"form": password_reset_form, "post_url": request.get_full_path() })
    else:   # initiate verification form
        password_reset_form = PasswordResetForm()
        messages.info(request, _("Please enter your email to verify"))
        return render(request, "parser_main/verifyCode.html", {"form": password_reset_form, "post_url": request.get_full_path() })

    # extractor

class AddPostView(LoginRequiredMixin, CreateView):
    model = Post
    form_class = PostForm
    template_name = 'parser_main/extractor.html'

def date_raw_handle(date_raw):  # take date string with form "Nov yyyy" and tranform it to form "yyyy-mm"
    count1 = 0
    count2 = 0
    date_array_return = []
    date_raw = date_raw.replace("Dates Employed", '')
    date_raw = date_raw.replace("Beschäftigungszeitraum", '')
    date_raw = date_raw.replace(".", '')
    date_array = date_raw.split(",")
    for i in range(len(date_array)):
        date_array[i] = date_array[i].replace(" ", "")
        if date_array[i].find("Jan") != -1:
            date_array[i] = date_array[i].replace("Jan", "")
            date_array[i] += '-01'
        elif date_array[i].find("Feb") != -1:
            date_array[i] = date_array[i].replace("Feb", "")
            date_array[i] += '-02'
        elif date_array[i].find("Mar") != -1:
            date_array[i] = date_array[i].replace("Mar", "")
            date_array[i] += '-03'
        elif date_array[i].find("Apr") != -1:
            date_array[i] = date_array[i].replace("Apr", "")
            date_array[i] += '-04'
        elif date_array[i].find("May") != -1:
            date_array[i] = date_array[i].replace("May", "")
            date_array[i] += '-05'
        elif date_array[i].find("Juli") != -1:
            date_array[i] = date_array[i].replace("Juli", "")
            date_array[i] += '-07'
        elif date_array[i].find("Juni") != -1:
            date_array[i] = date_array[i].replace("Juni", "")
            date_array[i] += '-06'
        elif date_array[i].find("Jun") != -1:
            date_array[i] = date_array[i].replace("Jun", "")
            date_array[i] += '-06'
        elif date_array[i].find("Jul") != -1:
            date_array[i] = date_array[i].replace("Jul", "")
            date_array[i] += '-07'
        elif date_array[i].find("Aug") != -1:
            date_array[i] = date_array[i].replace("Aug", "")
            date_array[i] += '-08'
        elif date_array[i].find("Sept") != -1:
            date_array[i] = date_array[i].replace("Sept", "")
            date_array[i] += '-09'
        elif date_array[i].find("Sep") != -1:
            date_array[i] = date_array[i].replace("Sep", "")
            date_array[i] += '-09'
        elif date_array[i].find("Oct") != -1:
            date_array[i] = date_array[i].replace("Oct", "")
            date_array[i] += '-10'
        elif date_array[i].find("Nov") != -1:
            date_array[i] = date_array[i].replace("Nov", "")
            date_array[i] += '-11'
        elif date_array[i].find("Dec") != -1:
            date_array[i] = date_array[i].replace("Dec", "")
            date_array[i] += '-12'
        elif date_array[i].find("Present") != -1:
            date_array[i] = date_array[i].replace("Present", datetime.now().strftime('%Y-%m'))
        elif date_array[i].find("Heute") != -1:
            date_array[i] = date_array[i].replace("Heute", datetime.now().strftime('%Y-%m'))
        elif len(date_array[i]) < 6 and len(date_array[i]) > 0:
            date_array[i] += '-01'
        elif date_array[i].find("März") != -1:
            date_array[i] = date_array[i].replace("März", "")
            date_array[i] += '-03'
        elif date_array[i].find("Mai") != -1:
            date_array[i] = date_array[i].replace("Mai", "")
            date_array[i] += '-05'
        elif date_array[i].find("Okt") != -1:
            date_array[i] = date_array[i].replace("Okt", "")
            date_array[i] += '-10'
        elif date_array[i].find("Dez") != -1:
            date_array[i] = date_array[i].replace("Dez", "")
            date_array[i] += '-12'
    for i in date_array:
        if (i == ""):
            break
        date_array_return.append([])
        if count2 == 2:
            count2 = 0
            count1 += 1
        date_array_return[count1].append(i)
        count2 += 1
    print(date_array_return)
    return date_array_return




# We have to distinguish between several things for our CVParser
# 1. The parser should know in which language it is parsing, since we are looking for keywords and these need to be understood by our program 
# 2. There are two different kinds of accounts in LinkedIn that differ in displaying some categories (different html code by the CKEditor) and
# we should be able to recognize both of them (accounts where 'skills' can't be unfolded and accounts where they can). Both can be parsed except
# for the experience which can't be parsed in the former case. That will be called the "not normal parser" whereas the second case will be called "normal parser".
# 3. In each case, English and German should both be handled.

@login_required
def UploadPost(request):
    """This method is the main parser. It requests the input from the editor body and extracts the information,
    then sends it back to the html"""

    # Get the post variables
    status = request.POST['status']

    # A profile was pasted which will now be parsed
    if status == 'extract':
        if 'body' in request.POST:
            body = request.POST['body']
        else:
            body = False

        # to start getting information from the source with the parser lxml
        soup = BeautifulSoup(body, "lxml")

        # find out if german or english language settings are used
        english = True
        if soup.find("a", string="Kontaktinformationen") is not None:
            english = False

        # find out which parser should be used, either normal (skills can be
        # unfolded) or not normal (skills will be in new page)
        normal_parser = False
        if english and soup.find("h2", string="Interests") is not None:     # This string does not exist in the not normal profile,
                                                                            # so it can be used to identify the required parser
            normal_parser = True
        elif not english and soup.find("h2", string="Interessen") is not None:
            normal_parser = True

        # now we need to search for the tags that have the information we need
        ######################################################################
        # search for the name
        name = []
        p_name = soup.find("h1", {})  # this is a one element list with h1 tag
        v_name = l_name = ""
        if p_name is not None:  # if you find h1
            name = p_name.text.split()      # Text of p_name contains first and last name (and possible middle names) which need to be split
            # possible middle name
            if len(name) > 2:
                for n in range (len(name) - 2):
                    v_name += name[n] + ' ' # first name for the database
                v_name += name[len(name)-2]
                l_name = name[len(name) - 1]  # last name for the database
            # no middle name
            else:
                v_name = name[0]    # first name for the database
                l_name = name[len(name) - 1]    # last name for the database


        # find the job name which is the next tag after p_name
        l_job = ""
        if p_name is not None:
            p_job = p_name.find_next_sibling("p")  # this is a list
            if p_job is not None:
                # if the next tag is the job
                if p_job.text.find(" ") == -1:
                    l_job = p_job.text  # add  this variable to the Database
                # if something is written after the name
                else:
                    p_job = p_job.find_next_sibling("p")
                    l_job = p_job.text  # add  this variable to the Database


        # for the experience
        dates_st = ""  # This variable will contain start date and end date
        all_experience = []
        # We use the keyword "Experience" to find the experiences
        if english:
            p_experience = soup.find(lambda tag: tag.name == "h2" and "Experience" in tag.text)
        else:
            p_experience = soup.find(lambda tag: tag.name == "h2" and "Berufserfahrung" in tag.text)

        # handle the case if we don't find the keyword "Experience"
        # Finding Experience by picking the tag that came after the keyword "Experience"
        if p_experience is not None:
            p_experience = p_experience.find_next_sibling("ul")
            if p_experience is not None:
                # get the clean text which splits the different experiences by \n\n
                l_experience = p_experience.text.split("\n\n")
                l_experience = [x for x in l_experience if (x != '')]

                if normal_parser:
                    employers = p_experience.find_all("h3")
                    for y in range(len(l_experience)):
                        # Split l_experience into a list with every characteristic for each experience
                        l_experience[y] = l_experience[y].split("\n")
                        l_experience[y] = [x for x in l_experience[y] if x != '']

                    for i in range(0, len(l_experience)):
                        # If the person has several experiences for the same company
                        # it must be parsed differently because the whole structure is different
                        try:
                            # in this case, "Title" will be found so use it to identify this case
                            if l_experience[i][0].find("Title") != -1 or l_experience[i][0].find("Titel") != -1:
                                # find the employer with several experiences
                                for j in range(len(employers)):
                                    if employers[j].text.find("Name des Unternehmens") != -1:
                                        employer_name = employers[j].text[21:]  # First 21 letters for "Name des Unternehmens" which need to be cut
                                                                                # because the employer is written after that
                                    if employers[j].text.find("Company Name") != -1:
                                        employer_name = employers[j].text[12:]  # First 12 letters for "Company Name" which need to be cut
                                                                                # because the employer is written after that

                                date_index = 0
                                for j in range(len(l_experience[i])):
                                    if l_experience[i][j].find("Dates Employed") != -1 or l_experience[i][j].find("Beschäftigungszeitraum") != -1:
                                        date_index = j

                                dates = l_experience[i][date_index].split('–')  # split by '-' because it separates the years
                                dates_start = nums_from_string.get_nums(dates[0])[0]    # the start year needs to be an integer
                                # This will later be appended to the all_experience list for each experience
                                current_experience = [l_experience[i][0][5:], employer_name, dates_start]   # First 5 letters of l_experience[i][0]
                                                                                                            # for "Title" or "Titel" which need to be cut
                                # Is there an end date as well?
                                if len(dates) > 1:
                                    if nums_from_string.get_nums(dates[1]):
                                        current_experience.append(nums_from_string.get_nums(dates[1])[0])
                                    else:
                                        current_experience.append(0)    # The 0 is later used to indicate that the person still works here
                                else:
                                    # experience terminated in the same year
                                    current_experience.append(nums_from_string.get_nums(dates[0])[0])
                                dates_st += dates[0] + ',' + dates[1] + ', '

                                # The experience doesn't always have to contain everything so every case needs to be handled
                                # which is a little bit messy. We want to get the job description if there is any.
                                if len(l_experience[i]) == 6:
                                    # in this case, the description is the last element because there can't be more than 6
                                    current_experience.append(l_experience[i][5])
                                elif len(l_experience[i]) == 5 or len(l_experience[i]) == 4:
                                    if (l_experience[i][len(l_experience[i]) - 1].find("Standort") == -1 and not english) or l_experience[i][len(l_experience[i]) - 1].find("Location") == -1 and english:
                                        # The last element is not the location
                                        if (l_experience[i][len(l_experience[i]) - 1].find("Beschäftigungsdauer") == -1 and not english) or (l_experience[i][len(l_experience[i]) - 1].find("Employment Duration") == -1 and english):
                                            # The last element is not the employment duration either so it must be the description
                                            # "See more" needs to be cut
                                            current_experience.append(l_experience[i][len(l_experience[i]) - 1].replace("…see more", "").replace("…Mehr anzeigen", ""))
                                    # Same as above but the other way around
                                    elif (l_experience[i][len(l_experience[i]) - 1].find("Beschäftigungsdauer") == -1 and not english) or (l_experience[i][len(l_experience[i]) - 1].find("Employment Duration") == -1 and english):
                                        if (l_experience[i][len(l_experience[i]) - 1].find("Standort") == -1 and not english) or l_experience[i][len(l_experience[i]) - 1].find("Location") == -1 and english:
                                            current_experience.append(l_experience[i][len(l_experience[i]) - 1].replace("…see more", '').replace("…Mehr anzeigen", ""))
                                    else:
                                        # No description
                                        current_experience.append('')
                                else:
                                    # If l_experience[i] has less than 4 elements there is no description
                                    current_experience.append('')
                                if current_experience[1] == '':
                                    continue
                                all_experience.append(current_experience)
                                continue
                        except:
                            print('exception')
                            continue

                        # not several experiences for the same company
                        try:
                            # try to find the date in the experience
                            dates = l_experience[i][3].split('–')   # start and end year (or 'today' if the person still works there)
                            # The employer name is embedded in the second element of l_experience[i] between some '\xa0'
                            employer_name = l_experience[i][2].split('\xa0')[0]
                            # The job title, employer name and start year can be appended
                            current_experience = [l_experience[i][0], employer_name, nums_from_string.get_nums(dates[0])[0]]
                            if len(dates) > 1:
                                # There is an end year or the person still works here
                                if nums_from_string.get_nums(dates[1]):
                                    # The end year is a number and not the word 'today'
                                    current_experience.append(nums_from_string.get_nums(dates[1])[0])
                                else:
                                    # The end year is not a number so it's the word 'today'
                                    current_experience.append(0)    # The 0 is later used to indicate that the person still works here
                            else:
                                # There is no end year so it's the same as the start year
                                current_experience.append(nums_from_string.get_nums(dates[0])[0])
                            dates_st += dates[0] + ',' + dates[1] + ', '

                        # if there is no date in the experience
                        except:
                            try:
                                employer_name = l_experience[i][2].split('\xa0')[0]
                                # The -1 is used later to indicate that there is no start or end year
                                current_experience = [l_experience[i][0], employer_name, -1, -1]
                            except:
                                continue

                        # Just like above, the experience doesn't always have to contain everything so every case needs to be handled
                        # which is a little bit messy. We want to get the job description if there is any.
                        if len(l_experience[i]) == 7:
                            # in this case, the description is the last element because there can't be more than 7
                            current_experience.append(l_experience[i][6].replace("…see more", '').replace("…Mehr anzeigen", ""))
                        elif len(l_experience[i]) == 6:
                            if l_experience[i][5].find("Standort") == -1 and not english or l_experience[i][5].find("Location") == -1 and english:
                                # The last element is not the location so it's the description
                                # 'See more' needs to be cut here as well
                                current_experience.append(l_experience[i][5].replace("…see more", '').replace("…Mehr anzeigen", ""))
                            else:
                                # The last element is the location so there is no description
                                current_experience.append('')
                        else:
                            # If l_experience[i] has less than 6 elements there is no description
                            current_experience.append('')
                        if current_experience[1] == '':
                            # It's not a job but a certificate which shouldn't be considered
                            continue
                        all_experience.append(current_experience)

                print("Erfahrungen: %s" % all_experience)


        # Find the location
        # Keywords to search for
        if english:
            p_location = soup.find("a", string="Contact info")
        else:
            p_location = soup.find("a", string="Kontaktinformationen")
        country = state = city = ""
        if p_location is not None:
            # The location can be found in the p tag of the html code
            p_location = p_location.find_parent("p")
            if p_location is not None:
                # The clean text contains the location separated by ','
                # 'Contact info' needs to be cut
                location = p_location.text.split(",")
                location[len(location) - 1] = location[len(location) - 1].replace("Kontaktinformationen", "")
                location[len(location) - 1] = location[len(location) - 1].replace("Contact info", "")

                # Not everyone has a complete location
                if len(location) == 3:
                    # A complete location
                    country = location[2]  # add  this variable to the Database
                    state = location[1]  # add  this variable to the Database
                    city = location[0]  # add  this variable to the Database
                elif len(location) == 2:
                    # Probably just the country and the city
                    country = location[1]
                    state = ""
                    city = location[0]
                elif len(location) == 1:
                    # Probably just the country
                    country = location[0]
                    state = city = ""
                else:
                    # No location
                    country = state = city = ""

        # Personal information
        info = ""
        # Keywords to search for
        if english:
            p_info = soup.find(lambda tag: tag.name == "h2" and "About" in tag.text)
        else:
            p_info = soup.find(lambda tag: tag.name == "h2" and "Info" in tag.text)
        if p_info is not None:
            p_info = p_info.find_next_sibling("p")
            if p_info is not None:
                info = p_info.text
        if not normal_parser:
            # With these type of accounts the information is written twice so it needs to be halved.
            if len(info) % 2 == 0:
                info = info[:len(info) // 2]
            else:
                info = info[:(len(info) + 1) // 2]

        # for the education
        all_education = []
        # Keywords to search for
        if english:
            p_education = soup.find(lambda tag: tag.name == "h2" and "Education" in tag.text)
        else:
            p_education = soup.find(lambda tag: tag.name == "h2" and "Ausbildung" in tag.text)
        if p_education is not None:
            p_education = p_education.find_next_sibling("ul")
            l_education = p_education.text.split("\n\n")
            l_education = list(filter(None, l_education))
            for i in range(len(l_education)):
                l_education[i] = l_education[i].split("\n")
                l_education[i] = list(filter(None, l_education[i]))
                # skip the grade
                if l_education[i][0].find("Note") != -1 or l_education[i][0].find("Grade") != -1:
                    continue

                if not normal_parser:
                    # duplicated string in !normal_parser -> remove the duplicated string
                    if len(l_education[i][0]) % 2 == 0:
                        l_education[i][0] = l_education[i][0][:len(l_education[i][0]) // 2]
                    else:
                        l_education[i][0] = l_education[i][0][:(len(l_education[i][0]) + 1) // 2]
                all_education.append(l_education[i][0])


        # for the skills
        # Keywords to search for
        if english:
            p_skills = soup.find(lambda tag: tag.name == "h2" and "Skills" in tag.text)
        else:
            p_skills = soup.find(lambda tag: tag.name == "h2" and "Kenntnisse" in tag.text)
        new_page = False
        skills = []
        skills_extra = []
        p_skills2 = p_skills
        if p_skills is not None:
            try:
                if not normal_parser:
                    p_skills = p_skills.find_next_sibling("ul")
                else:
                    p_skills = p_skills.find_next_sibling("ol")
                p_skills_temp = p_skills

                # add first skills
                p_skills = p_skills.find_all("li", {})
                if p_skills is not None:
                    for i in range(len(p_skills)):
                        skills.append((p_skills[i].find("p", {})).text)
                    # Clean up the list
                    skills[:] = [x for x in skills if ((x != "\n") and (x != ""))]

                # If the parser is not normal split each element in half
                if not normal_parser:
                    for i in range(len(skills)):
                        # shorten the skills string by the middots and then cut it in half to get the skill
                        name_of_skill = skills[i].split("·")[0]
                        skills[i] = name_of_skill[:(int(len(name_of_skill) / 2))]

                    # check if there are skills that can be opened in a new window
                    p_skills2 = p_skills2.find_next_sibling("ul")
                    p_skills2 = p_skills2.find_next_sibling("p")
                    if p_skills2 is not None and (p_skills2.text.find("See all") == 0 or p_skills2.text.find("Alle ") == 0):
                        new_page = True     # Used to indicate that there are more skills that will open in a new site

            except:
                print("No skills")


            if normal_parser:
                # add extra skills
                while True:
                    try:
                        p_skills_extra = p_skills_temp.find_next_sibling("h3").find_next_sibling("ol", {}).find_all("p")
                        skills_extra.append(p_skills_extra)
                        p_skills_temp = p_skills_temp.find_next_sibling("h3")
                        if p_skills_temp.find_next_sibling("h3").find_next_sibling("ol") is None:
                            break
                    except:
                        break
                for i in range(len(skills_extra)):
                    for j in range(len(skills_extra[i])):
                        skills.append(skills_extra[i][j].text)
        # Other people can endorse your skills hence why it needs to be removed.
        if english:
            skills = [x for x in skills if "given endorsement" not in x]
        else:
            skills = [x for x in skills if "diese Kenntnis bestätigt" not in x]

        # get image data
        img = ""
        prev = soup.find("h1", {})
        try:
            img = prev.find_previous_sibling("p")
            if img is not None:
                while True:
                    if img.img is not None:
                        link = img.img['src']
                        break
                    else:
                        img = img.find_previous_sibling("p")
        except:
            link = ''


        # formatting all_experience to string (self-explanatory)
        experience_str = ''
        if normal_parser:
            for i in range(len(all_experience)):
                if i != 0:
                    experience_str += "<br />"
                experience_str += "- "
                for j in range(len(all_experience[i])):
                    if all_experience[i][j] == -1:
                        continue
                    if all_experience[i][j] == 0:
                        if english:
                            experience_str += ' today)' + ' '
                        else:
                            experience_str += ' heute)' + ' '
                        continue
                    if j == 0:
                        experience_str += str(all_experience[i][j])
                    elif j == 2:
                        experience_str += ',' + ' ' + '(' + str(all_experience[i][j]) + '-'
                    elif j == 3:
                        experience_str += str(all_experience[i][j]) + ')'
                    else:
                        if not str(all_experience[i][j]):
                            continue
                        else:
                            experience_str += ',' + ' ' + str(all_experience[i][j])
        else:
            # Experiences can't get parsed if the user has a "not normal"
            # account because we could not identify an actual pattern in the html code.
            # The structure is pretty random. These sentences will be shown in the right window.
            if english:
                experience_str = "Not working for your profile. Please use " \
                                 "the edit tool."
            else:
                experience_str = "Nicht möglich für dieses Profil. Bitte " \
                                 "nutzen Sie die Edit-Seite."

        # formatting education to string (self-explanatory)
        education_str = ''
        for i in range(len(all_education)):
            if i != 0:
                education_str += "<br />"
            education_str += "- "
            education_str += all_education[i]

        # formatting skills to string (self-explanatory)
        try:
            skills_str = skills[0]
            for i in range(1, len(skills)):
                skills_str += ', ' + skills[i]
        except:
            skills_str = ''

        # format the data so it can be shown in the html
        if status == "extract":
            if english:
                name = "<strong>Name: </strong>" + v_name + " " + l_name
                livingAt = "<strong>Living place: </strong>" + city + state + country
                job = "<strong>Job: </strong>" + l_job
                info = "<strong>About: </strong>" + info
                education = "<strong>Education: </strong>" + education_str
                skills = "<strong>Skills: </strong>" + ''.join(skills_str)
                if normal_parser:
                    experience = "<strong>Experience: </strong>" + experience_str
                else:
                    experience = "<strong style=\"color:red;\">Experience: </strong>" + experience_str
                response = name + "<br />" + "<br />" + job + "<br />" + "<br />" + livingAt + "<br />" + "<br />" \
                           + info + "<br />" + "<br />" + experience + "<br />" + "<br />" + education + "<br />" + "<br />" \
                           + skills + "<br />" + "<br />"
                response_list: list[str | Any] = [response, link, v_name, l_name, city, state, country, l_job,
                                                  info, education_str, skills_str, experience_str, dates_st,
                                                  new_page]
                return JsonResponse(response_list, safe=False)
            elif not english:
                name = "<strong>Name: </strong>" + v_name + " " + l_name
                livingAt = "<strong>Ort: </strong>" + city + state + country
                job = "<strong>Arbeitsstelle: </strong>" + l_job
                info = "<strong>Info: </strong>" + info
                education = "<strong>Ausbildung: </strong>" + education_str
                skills = "<strong>Skills: </strong>" + ''.join(skills_str)
                if normal_parser:
                    experience = "<strong>Berufserfahrung: </strong>" + experience_str
                else:
                    experience = "<strong style=\"color:red;\">Berufserfahrung: </strong>" + experience_str
                response = name + "<br />" + "<br />" + job + "<br />" + "<br />" + livingAt + "<br />" + "<br />" \
                           + info + "<br />" + "<br />" + experience + "<br />" + "<br />" + education + "<br />" + "<br />" \
                           + skills + "<br />" + "<br />"

                response_list: list[str | Any] = [response, link, v_name, l_name, city, state, country, l_job,
                                                  info, education_str, skills_str, experience_str, dates_st,
                                                  new_page]
                return JsonResponse(response_list, safe=False)

    # Users with a "not normal" account can paste the missing skills in the reset window.
    # Those should now be parsed by this part of the method.
    elif status == "extract_skills":
        if 'body' in request.POST:
            body = request.POST['body']
        else:
            body = False
        soup = BeautifulSoup(body, "lxml")

        # Keywords to search for
        all_skills = soup.find(lambda tag: tag.name == "h2" and "Skills" in tag.text)
        if all_skills is None:
            all_skills = soup.find(lambda tag: tag.name == "h2" and "Kenntnisse" in tag.text)

        all_skills_list = []
        all_skills = all_skills.find_next_sibling("ul")
        all_skills = all_skills.find_all("p")
        for i in range(len(all_skills)):
            # append the text of every skill tag
            all_skills_list.append(all_skills[i].text)

        for i in range(len(all_skills_list)):
            if all_skills_list[i].find("Endorsed") != -1 or all_skills_list[i].find("Bestätigt") != -1:
                # Endorsement should be ignored
                all_skills_list[i] = ''
            else:
                name_of_skill = all_skills_list[i].split("·")[0]
                # Skill is doubled so it should be cut in half
                all_skills_list[i] = name_of_skill[:(int(len(name_of_skill) / 2))]
        # Clean up the list
        all_skills_list[:] = [x for x in all_skills_list if ((x != "\n") and (x != ""))]
        skills = all_skills_list

        # formatting skills to string
        try:
            skills_str = skills[0]
            for i in range(1, len(skills)):
                skills_str += ', ' + skills[i]
        except:
            skills_str = ''

        # get tha data from base to create a new output string with all skills
        firstName = request.POST['firstName1']
        lastName = request.POST['lastName1']
        city = request.POST['city1']
        state = request.POST['state1']
        country = request.POST['country1']
        job = request.POST['job1']
        info = request.POST['info1']
        image = request.POST['image1']
        edu_str = request.POST['edu_post1']

        name = "<strong>Name: </strong>" + firstName + " " + lastName
        livingAt = "<strong>Living at: </strong>" + city + state + country
        job = "<strong>Job: </strong>" + job
        info = "<strong>About: </strong>" + info
        education = "<strong>Education: </strong>" + edu_str
        skills = "<strong>Skills: </strong>" + ''.join(skills_str)
        experience = "<strong style=\"color:red;\">Experience: </strong>" + "Not working for your profile. Please use the edit tool."

        output_str = name + "<br />" + "<br />" + job + "<br />" + "<br />" + livingAt + "<br />" + "<br />" \
                     + info + "<br />" + "<br />" + experience + "<br />" + "<br />" + education + "<br />" + "<br />" \
                     + skills + "<br />" + "<br />"

        response_list: list[str | Any] = [output_str, skills_str]
        return JsonResponse(response_list, safe=False)
    elif status == "upload":
        firstName = request.POST['firstName']  # get first name string from base.html
        lastName = request.POST['lastName']
        city = request.POST['city']
        state = request.POST['state']
        country = request.POST['country']
        job = request.POST['job']
        info = request.POST['info']
        info = info.split("</strong>")[1]   #remove strong tags for the database
        image = request.POST['image']
        edu_str = request.POST['edu_post']
        skill_str = request.POST['skill_post']
        exp_str = request.POST['experience_post']
        edu_array = edu_str.split("<br />")
        skill_array = skill_str.split(", ")
        exp_array = exp_str.split("<br />")
        for i in exp_array:  # delete all false extracted experience (without date)
            x = re.findall("([1-2][0-9][0-9][0-9]-[1-2][0-9][0-9][0-9])", i)
            y = re.findall("([1-2][0-9][0-9][0-9]- today)", i)
            z = re.findall("([1-2][0-9][0-9][0-9]- heute)", i)
            if x or y or z:
                continue
            else:
                exp_array.remove(i)
        print(exp_array)
        date_raw = request.POST['date_post']
        print("####DATE###: %s\n" % date_raw)
        date_array = date_raw_handle(date_raw)
        try:
            post = Profile.objects.create(
                user=request.user,
                image=image,
                firstName=firstName,
                lastName=lastName,
                city=city,
                state=state,
                country=country,
                company="",
                jobTitle=job,
                info=info,
            )
            download_profile_image(profile=post)
            post.save()
            for i in range(len(edu_array)):  # loop for all education elements
                edu_array[i] = edu_array[i].replace("-", "")
                edu = Education.objects.create(
                    profile=post,
                    name=edu_array[i]
                )
                edu.save()

            for i in range(len(skill_array)):  # loop for all skill elements
                skills = Skill.objects.create(
                    profile=post,
                    name=skill_array[i]
                )
                skills.save()
            for i in range(len(exp_array)):
                if len(exp_array[i]) < 3:
                    continue
                else:
                    if len(date_array[i]) == 0:
                        break
                    elif len(date_array[i]) > 0:
                        print("date is ", date_array[i])
                        exp_array2 = exp_array[i].split(", ")
                        print("current array is ", exp_array2)
                        exp_array2[0] = exp_array2[0].replace("-", "")
                        if len(exp_array2) > 4:  # in case info has multiple comma
                            for k in range(4, len(exp_array2)):
                                exp_array2[3] += ', ' + exp_array2[k]
                        elif len(exp_array2) < 4:
                            exp_array2.append(" ")

                        exp = Experience.objects.create(
                            profile=post,
                            employer_name=exp_array2[1],
                            start_date=date_array[i][0],
                            end_date=date_array[i][1],
                            description=exp_array2[3],
                            job_title=exp_array2[0],
                        )
                        exp.save()
            response = "Profile added to server"
        except Exception as e:
            response = 'Something went wrong - ' + str(e)
        return JsonResponse(response, safe=False)
