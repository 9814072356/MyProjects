from django.contrib import admin
from .models import Education, Experience, Profile, Skill

# Register your models here.
admin.site.register(Profile)

admin.site.register(Education)
admin.site.register(Experience)
admin.site.register(Skill)