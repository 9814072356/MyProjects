from django import forms
from django.forms import TextInput
from django.forms.fields import EmailField
from django.forms.models import inlineformset_factory
from simpleUser.models import SimpleUser
from django.contrib.auth.forms import UserCreationForm
from .models import Profile, Education, Experience, Skill, VerificationCode
from crispy_forms.helper import FormHelper
from crispy_forms.layout import HTML, Column, Layout, Row
from crispy_forms.bootstrap import Div, Field, FieldWithButtons
from .models import Post
from django.utils.translation import gettext_lazy as _

class SimpleUserCreationForm(UserCreationForm):
    """A form for creating new users. Includes an email field, plus a repeated password."""

    class Meta:
        model = SimpleUser
        fields = ('email',)
        field_classes = {'email': EmailField}

class InlineFormHelper(FormHelper):
    """Crispy Forms Form Helper for InlineFormsets. Renders no form tag or csrf token."""
    def __init__(self, show_labels=False, *args, **kwargs):
        super().__init__(*args, **kwargs)

        self.form_tag = False
        self.disable_csrf = True
        self.render_hidden_fields = True
        self.form_show_labels = show_labels

class HorizontalInlineFormHelper(InlineFormHelper):
    """Variant of the InlineFormHelper to render as a bootstrap horizontal form"""
    def __init__(self, *args, **kwargs):
        super().__init__(*args, show_labels=True, **kwargs)

        self.form_class="form-horizontal"
        self.label_class="col-2"
        self.field_class="col"

FloatingField = Field # The forms previously used bootstrap 5 floating labels

class ProfileForm(forms.ModelForm):
    """A ModelForm for a profile that supports crispy forms rendering"""
    template_name = "parser_main/form_snippet.html"
    helper = InlineFormHelper(show_labels=True)
    helper.layout = Layout(
        Row(
            Column(FloatingField("firstName")), 
            Column(FloatingField("lastName"))
        ),
        Row(
            Column(FloatingField("city")), 
            Column(FloatingField("state")),
            Column(FloatingField("country"))
        ),
        Row(
            Column(FloatingField("jobTitle")),
            Column(FloatingField("company"))
        ),
        Row(
            Column(
                Field("image", template="parser_main/field_file_nohref.html"),
                FloatingField("info", rows=2)),
            HTML(
                """{% if image %}
                    <div class="col-sm-3">
                        <img src="data:{{ image }}" 
                        class="img-fluid img-thumbnail mb-3 float-right" 
                        alt="The format of the saved image can not be displayed">
                    </div>
                {% endif %}"""
            )
        )

    )
    helper.form_show_labels = True

    class Meta:
        model = Profile
        fields = ('firstName','lastName','city','state','country','image','company','jobTitle','info',)

class EducationForm(forms.ModelForm):
    template_name = "parser_main/form_snippet.html"

    class Meta:
        model = Education
        fields=("name",)

class ExperienceForm(forms.ModelForm):
    template_name = "parser_main/form_snippet.html"

    class Meta:
        model = Experience
        fields=("job_title", "start_date", "end_date", "employer_name", "description")

class SkillForm(forms.ModelForm):
    template_name = "parser_main/form_snippet.html"

    class Meta:
        model = Skill
        fields=("name",)


EduFormset = inlineformset_factory(Profile, Education, form=EducationForm, extra=0)
ExpFormset = inlineformset_factory(Profile, Experience, form=ExperienceForm, extra=0)
SkillFormset = inlineformset_factory(Profile, Skill, form=SkillForm, extra=0)

EduFormset.helper = InlineFormHelper(show_labels=False)
ExpFormset.helper = HorizontalInlineFormHelper()
SkillFormset.helper = InlineFormHelper(show_labels=False)

EduFormset.helper.layout = Layout(Div( # everything is rendered inside a div that can be duplicated
    "id", "profile", # render the hidden fields for each form in the div
    FieldWithButtons(
        "name", 
        Field("DELETE", template="parser_main/delete_button.html")
        
    )
))
ExpFormset.helper.layout = Layout(Div(
    "id", "profile", # render the hidden fields for each form in the div
    Field("job_title"),
    Field("employer_name"),
    Field("start_date", placeholder=_("Year"), template="parser_main/input_group.html"),
    Field("end_date", placeholder=_("Year"), template="parser_main/input_group.html"),
    Field("description", rows=2),
    # Use a single column to make float-right work
    Row(Column(Field("DELETE", template="parser_main/delete_button.html", extra_context={"extra_class" : " float-right"}))),
    css_class="p-3 mb-3 border border-secondary rounded"
))
SkillFormset.helper.layout = EduFormset.helper.layout

# class RP_form_for_account(forms.Form):
#     new_password = forms.CharField(widget=forms.PasswordInput)
#     confirm = forms.CharField(widget=forms.PasswordInput)

class VCodeForm(forms.Form):
    mail = EmailField()

class VeriCodeForm(forms.ModelForm):
    class Meta:
        model = VerificationCode
        fields= ('code',)

class PostForm(forms.ModelForm):
    def __init__(self, *args, **kwargs):
        super(PostForm, self).__init__(*args, **kwargs)
        self.fields['body'].label = ""

    class Meta:
        model = Post
        fields = ('body',)

        widgets = {
            'body': forms.Textarea(attrs={'class': 'form-control'}),
        }

        class PostForm(forms.ModelForm):
            def __init__(self, *args, **kwargs):
                super(PostForm, self).__init__(*args, **kwargs)
                self.fields['body'].label = ""

            class Meta:
                model = Post
                fields = ('body',)

                widgets = {
                    'body': forms.Textarea(attrs={'class': 'form-control'}),
                }
