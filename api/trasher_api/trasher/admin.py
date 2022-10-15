from django.contrib import admin
from .models import User, Item
 
@admin.register(User)
class User(admin.ModelAdmin):
  list_display = [field.name for field in
User._meta.get_fields()]



@admin.register(Item)
class Item(admin.ModelAdmin):
  list_display = [field.name for field in
Item._meta.get_fields()]
