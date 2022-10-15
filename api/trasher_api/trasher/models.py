from django.db import models

# Create your models here.


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
    image = models.ImageField()
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