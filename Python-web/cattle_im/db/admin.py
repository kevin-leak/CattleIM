from django.contrib import admin

# Register your models here.
from db import models

admin.site.register(models.User)
admin.site.register(models.Profile)
admin.site.register(models.Friends)
admin.site.register(models.Group)
admin.site.register(models.GroupMember)
admin.site.register(models.Tag)
admin.site.register(models.TagMember)
admin.site.register(models.LinkTask)
admin.site.register(models.LinkMember)
admin.site.register(models.LinkComplete)
admin.site.register(models.LinkComment)
admin.site.register(models.TimeLine)
admin.site.register(models.Conversation)
admin.site.register(models.Event)
admin.site.register(models.Apply)
admin.site.register(models.PushHistory)