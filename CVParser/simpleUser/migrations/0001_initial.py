# Generated by Django 3.2.9 on 2022-01-18 15:55

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='SimpleUser',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('password', models.CharField(max_length=128, verbose_name='password')),
                ('last_login', models.DateTimeField(blank=True, null=True, verbose_name='last login')),
                ('email', models.EmailField(max_length=320, unique=True, verbose_name='email address')),
                ('is_active', models.BooleanField(default=True)),
            ],
            options={
                'verbose_name': 'User',
            },
        ),
    ]
