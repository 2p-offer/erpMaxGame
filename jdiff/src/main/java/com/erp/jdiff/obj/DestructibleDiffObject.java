package com.erp.jdiff.obj;

/**
 * 继承此类后会带有一个isDestructible标记，调用destroy()方法更改标记状态，可使子类选择是否生成DiffContext
 *
 * @author Zhang Zhaoyuan
 * @date 2022/4/15 8:35 PM
 */
public class DestructibleDiffObject extends DiffObject implements IDestructible {

    /**
     * 每个继承此类的对象都会有一个isDestructible标记
     */
    private volatile boolean destructible;

    /**
     * 重写父类fieldChanged()方法
     *
     * @param field        属性名, 或 子节点key
     * @param changedValue 修改后的属性值
     * @param originValue  修改前的属性值
     */
    @Override
    protected void fieldChanged(String field, Object changedValue, Object originValue) {
        if (destructible) {
            return;
        }
        super.fieldChanged(field, changedValue, originValue);
    }

    /**
     * 返回isDestructible状态
     *
     * @return TRUE/FALSE
     */
    @Override
    public boolean isDestructible() {
        return destructible;
    }

    /**
     * 更改isDestructible状态
     */
    @Override
    public void destruct() {
        this.destructible = true;
    }
}
