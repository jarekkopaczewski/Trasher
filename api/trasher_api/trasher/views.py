from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.mixins import UpdateModelMixin, DestroyModelMixin

from .models import User, Item
from .serializers import UserSerializer, ItemSerializer


class UserView(APIView, UpdateModelMixin, DestroyModelMixin,):
    def get(self, request, id=None):
        if id:
            try:
                queryset = User.objects.get(id=id)
            except User.DoesNotExist:
                return Response({'errors': 'This user does not exist.'}, status=400)
            read_serializer = UserSerializer(queryset)
        else:
            queryset = User.objects.all()
            read_serializer = UserSerializer(queryset, many=True)
        return Response(read_serializer.data)
    
    def post(self, request):
        create_serializer = UserSerializer(data=request.data)
        print(request.data)
        try:
            same_user = User.objects.get(name=request.data.get('name').strip())
            return Response({'errors': 'User with the same user name already exists.'}, status=400)
        except User.DoesNotExist:
            pass
        except User.MultipleObjectsReturned:
            return Response({'errors': 'User with the same user name already exists.'}, status=400)
        if create_serializer.is_valid():
            user_object = create_serializer.save()
            read_serializer = UserSerializer(user_object)
            return Response(read_serializer.data, status=201)
        return Response(create_serializer.errors, status=400)

    def put(self, request, id=None):
        try:
            user_object = User.objects.get(id=id)
        except User.DoesNotExist:
            return Response({'errors': 'This user does not exist.'}, status=400)
        update_serializer = UserSerializer(user_object, data=request.data)
        if update_serializer.is_valid():
            user_object = update_serializer.save()
            read_serializer = UserSerializer(user_object)
            return Response(read_serializer.data, status=200)
        return Response(update_serializer.errors, status=400)

    def delete(self, request, id=None):
        try:
            user_object = User.objects.get(id=id)
        except User.DoesNotExist:
            return Response({'errors': 'This user does not exist.'}, status=400)
        user_object.delete()
        return Response(status=204)


class ItemView(APIView, UpdateModelMixin, DestroyModelMixin,):
    def get(self, request, id=None):
        if id:
            try:
                queryset = Item.objects.get(id=id)
            except Item.DoesNotExist:
                return Response({'errors': 'This item does not exist.'}, status=400)
            read_serializer = ItemSerializer(queryset)
        else:
            queryset = Item.objects.all()
            read_serializer = ItemSerializer(queryset, many=True)
        return Response(read_serializer.data)

    def post(self, request):
        create_serializer = ItemSerializer(data=request.data)
        if create_serializer.is_valid():
            item_object = create_serializer.save()
            read_serializer = ItemSerializer(item_object)
            return Response(read_serializer.data, status=201)
        return Response(create_serializer.errors, status=400)

    def put(self, request, id=None):
        try:
            item_object = Item.objects.get(id=id)
        except Item.DoesNotExist:
            return Response({'errors': 'This item does not exist.'}, status=400)
        update_serializer = ItemSerializer(item_object, data=request.data)
        if update_serializer.is_valid():
            item_object = update_serializer.save()
            read_serializer = ItemSerializer(item_object)
            return Response(read_serializer.data, status=200)
        return Response(update_serializer.errors, status=400)

    def delete(self, request, id=None):
        try:
            item_object = Item.objects.get(id=id)
        except Item.DoesNotExist:
            return Response({'errors': 'This item does not exist.'}, status=400)
        item_object.delete()
        return Response(status=204)
