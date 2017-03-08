/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.Operation;

/**
 *只是一个接口
 * @author pisces312
 */
public interface OperationFactory {

    /**
     * 普通公有方法，无参数
     * 无选择语句
     * 只有一个“产品”
     * @param opr
     * @return
     */
    public Operation createOperation();
}
