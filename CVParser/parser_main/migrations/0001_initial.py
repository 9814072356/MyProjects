# Generated by Django 3.2.9 on 2022-01-18 15:54

import ckeditor.fields
from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion
import month.models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Post',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('body', ckeditor.fields.RichTextField(blank=True, null=True)),
            ],
        ),
        migrations.CreateModel(
            name='Profile',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('firstName', models.CharField(max_length=300, verbose_name='First name')),
                ('lastName', models.CharField(max_length=300, verbose_name='Last name')),
                ('city', models.CharField(blank=True, max_length=300, verbose_name='City')),
                ('state', models.CharField(blank=True, max_length=300, verbose_name='State')),
                ('country', models.CharField(blank=True, max_length=300, verbose_name='Country')),
                ('image', models.ImageField(blank=True, max_length=500, null=True, upload_to='image', verbose_name='Photo')),
                ('company', models.CharField(blank=True, max_length=200, verbose_name='Company')),
                ('jobTitle', models.CharField(blank=True, max_length=300, verbose_name='Job')),
                ('info', models.TextField(blank=True)),
                ('user', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='VerificationCode',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('code', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='Skill',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=300, verbose_name='Skill')),
                ('profile', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='parser_main.profile')),
            ],
        ),
        migrations.CreateModel(
            name='Experience',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('start_date', month.models.MonthField(blank=True, null=True, verbose_name='from')),
                ('end_date', month.models.MonthField(blank=True, null=True, verbose_name='till')),
                ('employer_name', models.CharField(max_length=512, verbose_name='Employer')),
                ('description', models.TextField(blank=True, verbose_name='Description')),
                ('job_title', models.CharField(blank=True, max_length=512, verbose_name='Job')),
                ('profile', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='parser_main.profile')),
            ],
        ),
        migrations.CreateModel(
            name='Education',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=1024, verbose_name='Education')),
                ('profile', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='parser_main.profile')),
            ],
        ),
    ]