BaseContract : interface View; interface Presenter;

BasePresenter<T extends Contact.Presenter> --> extends BaseContrat.Presenter;

BasePresenterFragment<Persenter extends Contact.Presenter> --> extends BaseFragment implements Contact.View<Presenter>

LoginContacter --> interface View extends BaseContact.View, interface Presenter extends Contact.Presnter;

LoginPresenter --> extend BasePresenter implements LoginContacter.Presenter;

LoginFragment --> extends BasePresenterFragment<Presenter extends LoginContact.Presenter> implements LoginContact.View



BaseContact 分为两部最为基础的协议， 针对于View, Presenter
BasePresenter 是对BaseContact.Present进一步的封装, 目的是抽离出所有presenter的共性
BasePresenterFragment 继承了自定义的BaseFragment，扩展了BaseContact.View 的方法特性， 目的是在Fragment这一层次对Present的封装
LoginContact 对于Login这一操作建立契约
LoginPresenter 继承了BasePresent的性质， 实现自身合约自带的功能LoginContact.Presenter
LoginFragment 继承了Fragment这一层 Presenter View 都需要具备的属性以及要求, 同时实现Login这一操作view的契约

这使得LoginContact的形容非常恰当不用多写一些BasePresenter的方法进去

view 和 present的传递设置在BasePresenter中
