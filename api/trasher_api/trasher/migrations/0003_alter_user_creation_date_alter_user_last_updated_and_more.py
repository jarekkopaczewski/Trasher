# Generated by Django 4.1.2 on 2022-10-15 16:18

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('trasher', '0002_alter_user_name'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='creation_date',
            field=models.DateTimeField(),
        ),
        migrations.AlterField(
            model_name='user',
            name='last_updated',
            field=models.DateTimeField(),
        ),
        migrations.AlterField(
            model_name='user',
            name='name',
            field=models.CharField(max_length=30, unique=True),
        ),
    ]
