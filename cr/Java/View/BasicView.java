package cr.Java.View;

import cr.Java.Contrel.BasicControl;


public interface BasicView {
    public void start(BasicControl control, String text);

    public BasicControl getControl();
}
