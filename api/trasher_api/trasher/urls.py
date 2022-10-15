from django.urls import path

from . import views

urlpatterns = [
  path('api/user/', views.UserView.as_view()),
  path('api/user/<int:id>/', views.UserView.as_view()),
  path('api/item/', views.ItemView.as_view()),
  path('api/item/<int:id>/', views.ItemView.as_view()),
]