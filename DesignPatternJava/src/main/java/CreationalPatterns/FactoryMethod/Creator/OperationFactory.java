/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CreationalPatterns.FactoryMethod.Creator;

import CreationalPatterns.FactoryMethod.Product.Operation;

/**
 *ֻ��һ���ӿ�
 * @author pisces312
 */
public interface OperationFactory {

    /**
     * ��ͨ���з������޲���
     * ��ѡ�����
     * ֻ��һ������Ʒ��
     * @param opr
     * @return
     */
    public Operation createOperation();
}
