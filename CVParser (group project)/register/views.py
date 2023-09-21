from django.shortcuts import render, redirect
from .forms import RegisterCreationForm
from simpleUser.models import SimpleUserManager, SimpleUser
# Create your views here.

def register(response):
    print(response.method)
    if response.method == "POST":
        form = RegisterCreationForm(response.POST)
        if form.is_valid():
            form.save()

        SimpleUser.objects.create_user(form.cleaned_data["email"], form.clean_password2()) #funktioniert noch nicht
        return redirect("/login")
    else: 
        form = RegisterCreationForm()
    return render(response, "register/register.html", {"form":form})





