from django.db import models


import os
from uuid import uuid4

def path_and_rename(path):
    def wrapper(instance, filename):
        ext = filename.split('.')[-1]
        # get filename
        if instance.pk:
            filename = '{}.{}'.format(instance.pk, ext)
        else:
            # set filename as random string
            filename = '{}.{}'.format(uuid4().hex, ext)
        # return the whole path to the file
        return os.path.join(path, filename)
    return wrapper


class User(models.Model):
    id = models.AutoField(
        primary_key=True
    )
    name = models.CharField(
        max_length=30,
        null=True
    )
    latitude = models.FloatField(null=True)
    longitude = models.FloatField(null=True)
    score = models.IntegerField(null=True)
    creation_date = models.DateTimeField(
        auto_now_add=True,
        null=False,
        blank=False
    )
    last_updated = models.DateTimeField(
        auto_now=True,
        null=False,
        blank=False
    )


class Item(models.Model):
    id = models.AutoField(
        primary_key=True
    )
    name = models.CharField(
        max_length=30,
        null=True
    )
    latitude = models.FloatField(null=True)
    longitude = models.FloatField(null=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    status = models.BooleanField(null=True)
    category = models.CharField(
        max_length=30,
        null=True
    )
    image = models.ImageField(upload_to=path_and_rename('static/images'))
    likes = models.IntegerField(null=True)
    views = models.IntegerField(null=True)
    creation_date = models.DateTimeField(
        auto_now_add=True,
        null=False,
        blank=False
    )
    last_updated = models.DateTimeField(
        auto_now=True,
        null=False,
        blank=False
    )