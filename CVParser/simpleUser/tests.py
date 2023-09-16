from django.contrib import auth
from django.db.models import manager
from django.test import TestCase
from django.core.exceptions import ValidationError

from django.contrib.auth import get_user_model, authenticate, login

from .models import SimpleUser, SimpleUserManager

class SimpleUserTests(TestCase):

    def test_user_model_setting(self):
        self.assertIs(get_user_model(), SimpleUser)

    manager = SimpleUser.objects

    def test_create_user(self):
        email = __name__ + "@example.com"
        password = "1234"
        user = self.manager.create_user(email = email, password = password)

        self.assertIsNotNone(user)

        user = SimpleUser.objects.get(email=email)

        self.assertEquals(email, user.email)

    def test_invalid_email(self):
        with self.assertRaises(ValidationError):
            self.manager.create_user("notAnEmail", "notAPassword")

    def test_no_password(self):
        user = self.manager.create_user(__name__ + "@example.com")
        #TODO Think about the no password case

    def test_login(self):
        email = __name__ + "@example.com"
        password = "1234"
        user = self.manager.create_user(email=email, password = password)

        self.assertTrue(self.client.login(email=email, password=password))
    
    def test_wrong_email(self):
        email = __name__ + "@example.com"
        password = "1234"
        user = self.manager.create_user(email=email, password = password)

        self.assertFalse(self.client.login(email="not_my_email@example.com", password=password))
    
    def test_wrong_password(self):
        email = __name__ + "@example.com"
        password = "1234"
        user = self.manager.create_user(email=email, password = password)

        self.assertFalse(self.client.login(email=email, password="not_my_password"))
