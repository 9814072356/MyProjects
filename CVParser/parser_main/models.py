from django.db import models
from django.contrib.auth import get_user_model
from ckeditor.fields import RichTextField
from month.models import MonthField
from month.widgets import MonthSelectorWidget
from django.urls import reverse
from django.utils.translation import gettext_lazy as _

User = get_user_model()
# Create your models here.
class Post(models.Model):
    body = RichTextField(blank=True, null=True)

    def __str__(self):
        return f'{self.firstName} {self.lastName}'

    def get_absolute_url(self):
        return reverse('tool')


class Profile(models.Model):
    """This class creates a profile"""

    user = models.ForeignKey(User, models.CASCADE, null=True)

    firstName = models.CharField(max_length=300, verbose_name=_("First name"))
    lastName = 	models.CharField(max_length=300, verbose_name=_("Last name"))
    city = 		models.CharField(max_length=300, blank=True, verbose_name=_("City"))
    state = 	models.CharField(max_length=300, blank=True, verbose_name=_("State"))
    country = 	models.CharField(max_length=300, blank=True, verbose_name=_("Country"))
    image = 	models.ImageField(max_length=500,upload_to="image", null=True, blank=True, verbose_name=_("Photo"))
    company = 	models.CharField(max_length=200, blank=True, verbose_name=_("Company"))
    jobTitle = 	models.CharField(max_length=300, blank=True, verbose_name=_("Job"))
    info = 		models.TextField(blank=True)
    # education = models.ManyToManyField(Education, blank=True)
    # skills = models.ManyToManyField(Skill, blank=True)
    # experience = models.ManyToManyField(Experience, blank=True)

    def __str__(self):
        return f'{self.firstName} {self.lastName}'


class Education(models.Model):
    """The class is for the education list"""

    profile = models.ForeignKey(Profile, models.CASCADE, null=True)
    name = models.CharField(max_length=1024, verbose_name=_("Education"))

    def __str__(self):
        return self.name


class Skill(models.Model):
    """The class is for the skills list"""

    profile = models.ForeignKey(Profile, models.CASCADE, null=True)
    name = models.CharField(max_length=300, verbose_name=_("Skill"))

    def __str__(self):
        return self.name


class Experience(models.Model):
    """The class is for the experience list"""

    profile = models.ForeignKey(Profile, models.CASCADE, null=True)
    start_date = 	MonthField(null=True, blank=True, verbose_name=_("from"))
    end_date = 		MonthField(null=True, blank=True, verbose_name=_("till"))
    employer_name = models.CharField(max_length=512, verbose_name=_("Employer"))
    description = 	models.TextField(blank=True, verbose_name=_("Description"))
    job_title = 	models.CharField(max_length=512, blank=True, verbose_name=_("Job"))

    start_date.widget = MonthSelectorWidget({'class' : "form-control"})
    end_date.widget = MonthSelectorWidget({'class' : "form-control"})

    def __str__(self):
        return self.job_title

class VerificationCode(models.Model):
    code = models.IntegerField()

    def __str__(self):
        return self.code
