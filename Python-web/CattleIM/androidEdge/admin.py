from django.contrib import admin

# Register your models here.
from androidEdge import models

admin.site.register(models.User)
admin.site.register(models.Profile)
admin.site.register(models.Friends)
admin.site.register(models.Group)
admin.site.register(models.GroupMember)
admin.site.register(models.Tag)
admin.site.register(models.TagMember)
admin.site.register(models.LinkTask)
admin.site.register(models.TimeLine)
admin.site.register(models.Event)