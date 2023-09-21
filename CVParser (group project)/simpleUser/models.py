from tabnanny import verbose
from django.conf import settings
from django.db import models
from django.contrib.auth.models import AbstractBaseUser
from django.contrib.auth.models import BaseUserManager

from django.utils.translation import gettext_lazy

class SimpleUserManager(BaseUserManager):
    
    def create_user(self, email, password=None):
    
        if not email:
            raise ValueError('All users must have a valid email address')
        
        user = self.model(email=self.normalize_email(email))
        
        user.set_password(password)
        user.full_clean()
        user.save(using=self._db)
        
        return user
    
    def create_superuser(self, email, password=None):
        return self.create_user(email, password)

class SimpleUser(AbstractBaseUser):
    "A simple User model identifying users by email address only"

    email = models.EmailField(
        verbose_name=gettext_lazy('email address'),
        max_length=320,
        unique=True,
    )
    
    is_active = models.BooleanField(default=True)
    
    objects = SimpleUserManager()
    
    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []
    
    def __str__(self):
        return self.email
    
    def has_perm(self, perm, obj=None):
        return True
    
    def has_module_perms(self, app_label):
        return True
    
    @property
    def is_staff(self):
        "Property indicating if a user can access the admin site"
        return settings.DEBUG or False # Access to admin page only in DEBUG mode
    
    class Meta:
        verbose_name = gettext_lazy("User")
    
