import uuid

from django.contrib.auth.models import AbstractUser, AbstractBaseUser, PermissionsMixin, UserManager
from django.contrib.auth.validators import UnicodeUsernameValidator
from django.core.mail import send_mail
from django.db import models
from django.utils import timezone


# Create your models here.


class Profile(models.Model):
    push_id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    app_id = models.CharField(max_length=255, null=True, blank=True, unique=True)
    is_bind = models.BooleanField(default=False)
    choice = ((0, '男'), (1, '女'))
    sex = models.IntegerField(choices=choice, null=True, blank=True)
    desc = models.TextField(max_length=100, null=True, blank=True)


#  电话号码，头像， userName
class User(AbstractBaseUser, PermissionsMixin):
    uid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    phone = models.CharField(max_length=11, null=False, unique=True, db_index=True)
    avatar = models.FileField(upload_to="avatars/", default="avatars/default.jpg", verbose_name="头像", blank=True)
    profile = models.OneToOneField(to="Profile", to_field="push_id", null=True, blank=False, on_delete=models.SET_NULL)
    create_time = models.DateTimeField(auto_now=True)

    username_validator = UnicodeUsernameValidator()
    USERNAME_FIELD = 'phone'
    username = models.CharField(
        "username",
        max_length=20,
        help_text="Required. 10 characters or fewer. Letters, digits and @/./+/-/_ only.",
        validators=[username_validator],
    )
    email = models.EmailField("email address", blank=True)
    is_staff = models.BooleanField(
        "staff status",
        default=False,
        help_text="Designates whether the user can log into this admin site.",
    )
    is_active = models.BooleanField(
        "active",
        default=True,
        help_text=(
            'Designates whether this user should be treated as active. '
            'Unselect this instead of deleting accounts.'
        ),
    )
    date_joined = models.DateTimeField('date joined', default=timezone.now)

    objects = UserManager()

    EMAIL_FIELD = 'email'
    REQUIRED_FIELDS = ['email']

    class Meta:
        verbose_name = "用户"
        verbose_name_plural = "用户"
        swappable = 'AUTH_USER_MODEL'

    def clean(self):
        super().clean()
        self.email = self.__class__.objects.normalize_email(self.email)

    def get_full_name(self):
        """
        Return the first_name plus the last_name, with a space in between.
        """
        full_name = '%s %s' % self.username
        return full_name.strip()

    def email_user(self, subject, message, from_email=None, **kwargs):
        """Send an email to this user."""
        send_mail(subject, message, from_email, [self.email], **kwargs)


# message friend
class Friends(models.Model):
    fid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    origin = models.ForeignKey(to="User", to_field="uid", null=False, on_delete=models.CASCADE,
                               related_name='origin_user_id')
    target = models.ForeignKey(to="User", to_field="uid", null=False, on_delete=models.CASCADE,
                               related_name="target_user_id")
    alias = models.CharField(max_length=20)


class Group(models.Model):
    gid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    owner = models.ForeignKey(to="User", to_field='uid', null=False, on_delete=models.CASCADE)
    name = models.CharField(max_length=20, null=False)
    picture = models.FileField(upload_to='groupPicture/', null=False)
    description = models.CharField(max_length=100, blank=True)
    update_at = models.DateTimeField(auto_now_add=True)


class GroupMember(models.Model):
    mid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    alias = models.CharField(max_length=20)
    group = models.ForeignKey(to="Group", to_field="gid", null=False, on_delete=models.CASCADE)
    user = models.ForeignKey(to="User", to_field="uid", null=False, on_delete=models.CASCADE)
    # 管理权限
    permission_type = models.IntegerField(default=0)
    # 通知权限
    notify_level = models.IntegerField(default=0)
    createAt = models.DateTimeField(auto_now_add=True)
    upDateAt = models.DateTimeField(auto_now=True)


# tag的所属可能是个人，也可能是一个群， group可以没有
class Tag(models.Model):
    tid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    create = models.ForeignKey(to=User, on_delete=models.CASCADE)
    group = models.ForeignKey(to=Group, null=True, on_delete=models.CASCADE)
    tag_name = models.CharField(max_length=20, null=False)
    description = models.CharField(max_length=100, blank=True)
    update_at = models.DateTimeField(auto_now_add=True)


class TagMember(models.Model):
    tag = models.ForeignKey(to="Tag", to_field="tid", null=False, on_delete=models.CASCADE)
    tmId = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    user = models.ForeignKey(to="User", to_field="uid", null=False, on_delete=models.CASCADE)


class TimeLine(models.Model):
    user = models.ForeignKey(to="User", to_field='uid', null=False, on_delete=models.CASCADE)
    content = models.TextField(null=False)
    create_time = models.DateTimeField(auto_now_add=True)
    start_time = models.DateTimeField(auto_now_add=True)
    end_time = models.DateTimeField(null=False)

    class Meta:
        unique_together = ('start_time', 'end_time',)


class LinkTask(models.Model):
    lid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    create = models.ForeignKey(to="User", to_field="uid", on_delete=models.CASCADE)
    group = models.ForeignKey(to=Group, null=True, on_delete=models.CASCADE)
    tag = models.ForeignKey(to=Tag, null=True, on_delete=models.CASCADE)
    type = models.IntegerField()
    content = models.TextField(null=False)
    attach = models.CharField(max_length=255)
    member_count = models.IntegerField()
    create_time = models.DateTimeField(auto_now_add=True)
    start_time = models.DateTimeField(auto_now_add=True)
    end_time = models.DateTimeField(null=False)


class LinkMember(models.Model):
    mid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    link = models.ForeignKey(to="LinkTask", to_field="lid", null=False, on_delete=models.CASCADE)
    user = models.ForeignKey(to="User", to_field="uid", null=False, on_delete=models.CASCADE)
    is_up = models.BooleanField(default=False)
    is_remind = models.BooleanField(default=True)


class LinkComment(models.Model):
    link_id = models.ForeignKey(to="LinkTask", to_field="lid", null=False, on_delete=models.CASCADE)
    comment = models.ForeignKey(to="self", related_name='lower_comment', blank=True, on_delete=models.CASCADE)
    link_from = models.ForeignKey(to="LinkMember", null=False, on_delete=models.CharField,
                                  related_name="from_member_id")
    link_to = models.ForeignKey(to="LinkMember", null=False, on_delete=models.CharField, related_name="to_member_id")
    create_time = models.DateTimeField(auto_now_add=True)


class LinkComplete(models.Model):
    receive_id = models.ForeignKey(to="LinkTask", to_field="lid", null=False, on_delete=models.CASCADE)
    attach = models.CharField(max_length=255)
    content = models.TextField()
    type = models.IntegerField()
    create_time = models.DateTimeField(auto_now_add=True)


class Conversation(models.Model):
    cid = models.UUIDField(primary_key=True, default=uuid.uuid4, unique=True)
    content = models.CharField(max_length=255, null=False, blank=True)
    send = models.ForeignKey(to=User, null=False, on_delete=models.CASCADE, related_name="send_event_id")
    receive = models.ForeignKey(to=User, null=True, on_delete=models.CASCADE, related_name="receive_event_id")
    category_list = (
        (0, '文本'),
        (1, '图片'),
        (2, '语音'),
        (3, '文件')
    )
    category = models.IntegerField(choices=category_list)
    update_time = models.DateTimeField(auto_now=True)


# 在消息接口中 chatId  指的是 group， tag， conversation， link, 根据type来填充
class Event(models.Model):
    eid = models.UUIDField(primary_key=True, default=uuid.uuid4, unique=True)

    conversation = models.ForeignKey(to=Conversation, on_delete=models.CASCADE)
    chatId = models.UUIDField(default=uuid.uuid4, null=True, blank=True, editable=True)

    type_list = (
        (0, '系统消息'),
        (1, '普通消息'),
        (2, '请求消息'),
        (3, '主题消息'),
        (4, '公告消息'),
        (5, '任务消息'),
        (6, '关联消息'),
        (7, '群消息')
    )

    type = models.IntegerField(choices=type_list)
    update_time = models.DateTimeField(auto_now=True)


# targetId 不进行强关联， description描述：我想添加你为好友
class Apply(models.Model):
    aid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    application = models.ForeignKey(to='User', null=True, on_delete=models.CASCADE)
    targetId = models.CharField(max_length=255)
    attach = models.TextField()
    description = models.CharField(max_length=255)
    type = models.IntegerField()
    create_time = models.DateTimeField(auto_now_add=True)
    update_time = models.DateTimeField(auto_now=True)


# 用entity_type 来确定发送的消息是群消息,评论，系统消息，用户信息修改消息（比如群员修改昵称），task建立
# receive_push_id 用来确定设备是哪个设备，手机还是网页端
# arrival_time 回送，告诉后端已经收到消息
# entity 是 comment 与 event 转化为Jason的文本
# receive_push  是否接受到消息
# arrival_time_plan 用来处理定时任务
class PushHistory(models.Model):
    pid = models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True)
    entity = models.TextField()
    send = models.ForeignKey(to=Profile, to_field="push_id", null=False, on_delete=models.CASCADE,
                             related_name="send_push_id")
    receive = models.ForeignKey(to=Profile, to_field="push_id", null=True, on_delete=models.CASCADE,
                                related_name="receive_push_id")
    receive_push = models.BooleanField(default=False)
    arrival_time_plan = models.DateTimeField(null=True, blank=True)
    arrival_time = models.DateTimeField(null=True, blank=True)
    create_time = models.DateTimeField(auto_now_add=True)
    update_time = models.DateTimeField(auto_now=True)

# todo 及客户资料表， 以及课表，月计划，归档问题处理，event中id的修改不自动生成（56）


# todo 处理avatar的问题，是以字符串存还是图像本省
