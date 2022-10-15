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
        instance.name = validated_data.get(
            'name', instance.name)
        instance.latitude = validated_data.get(
            'latitude', instance.latitude)
        instance.longitude = validated_data.get('longitude', instance.longitude)
        instance.score = validated_data.get('score', instance.score)
        instance.save()
        return instance

    class Meta:
        model = User
        fields = '__all__' 

class ItemSerializer(serializers.ModelSerializer):
    def create(self, validated_data):
        return User.objects.create(
            name=validated_data.get('name'),
            latitude=validated_data.get('latitude'),
            longitude=validated_data.get('longitude'),
            score=validated_data.get('score'),
        )

    def update(self, instance, validated_data):
        instance.name = validated_data.get(
            'name', instance.name)
        instance.latitude = validated_data.get(
            'latitude', instance.latitude)
        instance.longitude = validated_data.get('longitude', instance.longitude)
        instance.score = validated_data.get('score', instance.score)
        instance.save()
        return instance

    class Meta:
        model = User
        fields = '__all__' 