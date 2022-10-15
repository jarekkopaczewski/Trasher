from rest_framework import serializers

from .models import User, Item


class UserSerializer(serializers.ModelSerializer):
    def create(self, validated_data):
        return User.objects.create(
            name=validated_data.get('name'),
            latitude=validated_data.get('latitude'),
            longitude=validated_data.get('longitude'),
            score=validated_data.get('score'),
        )

    def update(self, instance, validated_data):
        instance.name = validated_data.get('name', instance.name)
        instance.latitude = validated_data.get('latitude', instance.latitude)
        instance.longitude = validated_data.get('longitude', instance.longitude)
        instance.score = validated_data.get('score', instance.score)
        instance.save()
        return instance

    class Meta:
        model = User
        fields = '__all__' 


class ItemSerializer(serializers.ModelSerializer):
    def create(self, validated_data):
        return Item.objects.create(
            name=validated_data.get('name'),
            latitude=validated_data.get('latitude'),
            longitude=validated_data.get('longitude'),
            user=validated_data.get('user'),
            status=validated_data.get('status'),
            category=validated_data.get('category'),
            image=validated_data.get('image'),
            likes=validated_data.get('likes'),
            creation_date=validated_data.get('creation_date'),
            last_updated=validated_data.get('last_updated')
        )

    def update(self, instance, validated_data):
        instance.name = validated_data.get(
            'name', instance.name)
        instance.latitude = validated_data.get(
            'latitude', instance.latitude)
        instance.longitude = validated_data.get('longitude', instance.longitude)
        instance.user = validated_data.get('user', instance.user)
        instance.status = validated_data.get('status', instance.status)
        instance.category = validated_data.get('category', instance.category)
        instance.image = validated_data.get('image', instance.image)
        instance.likes = validated_data.get('likes', instance.likes)
        instance.creation_date = validated_data.get('creation_date', instance.creation_date)
        instance.last_updated = validated_data.get('last_updated', instance.last_updated)
        instance.save()
        return instance

    class Meta:
        model = Item
        fields = '__all__'

