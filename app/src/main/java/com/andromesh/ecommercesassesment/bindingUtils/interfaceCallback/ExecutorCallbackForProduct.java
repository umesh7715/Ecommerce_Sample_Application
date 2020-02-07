package com.andromesh.ecommercesassesment.bindingUtils.interfaceCallback;

import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;

public interface ExecutorCallbackForProduct {

    public void fromDiskIO(RankingProduct productRanking);

    public void fromNetwork();

    public void fromMain();
}
