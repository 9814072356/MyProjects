from django import forms
from django.contrib.auth.forms import UserCreationForm, UserChangeForm
from simpleUser.models import SimpleUser


class RegisterCreationForm(UserCreationForm):
    email = forms.EmailField()

    class Meta:
        model = SimpleUser
        fields = [ "email", ]

class RegisterChangeForm(UserChangeForm):
    email = forms.EmailField()

    class Meta:
        model = SimpleUser
        fields = [ "email", ]

