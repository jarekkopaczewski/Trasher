from django.db import models
import os
from uuid import uuid4
from django.utils.deconstruct import deconstructible


@deconstructible
class PathAndRename(object):
    def __init__(self, sub_path):
        self.path = sub_path

    def __call__(self, instance, filename):
        ext = filename.split('.')[-1]
        filename = '{}.{}'.format(uuid4().hex, ext)
        return os.path.join(self.path, filename)

path_and_rename = PathAndRename("./static/images")


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
    image = models.ImageField(upload_to=path_and_rename)
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