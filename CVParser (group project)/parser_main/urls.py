from django.urls import path
from . import views
from django.contrib.auth.views import LogoutView
from django.contrib.auth import views as auth_views

app_name = "parser_main"
urlpatterns = [
    path("register/", views.sign_up, name="register"),
    path("edit/", views.edit, name="edit"),
    path("login/", views.sign_in),
    path('', views.sign_in, name="login"),
    path("export/", views.render_pdf_view, name="export"),
    path("logout/", LogoutView.as_view(), name="logout"),
    path("verifycode/", views.send_code, name="send"),
    path("about/", views.about, name="about"),
    # path("account/", views.account, name="account"),
    path("account/",auth_views.PasswordChangeView.as_view(template_name='parser_main/account.html'),name="account"),
    path("password_change/done/", auth_views.PasswordChangeView.as_view(template_name='parser_main/account.html'), name="password_change_complete"),
    path("verifyMail/", views.verify, name="reset"),
    path("reset/<uidb64>/<token>/", auth_views.PasswordResetConfirmView.as_view(template_name="parser_main/reset_password.html"), name="password_reset_confirm"),
    path('reset/done/', auth_views.PasswordResetCompleteView.as_view(template_name='parser_main/alert.html'), name='password_reset_complete'),
    path('extractor/', views.AddPostView.as_view(), name="extractor"),
	path('extractor/upload/', views.UploadPost),
]