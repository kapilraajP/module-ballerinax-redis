/*
 * Copyright (c) 2018, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.orglicensesLICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.redis.actions.zset;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.RedisDataSourceUtils;
import org.ballerinalang.redis.actions.AbstractRedisAction;

/**
 * {@code {@link ZRevRangeByScore}} Maps with "ZREVRANGEBYSCORE" operation of Redis.
 *
 * @since 0.5.0
 */
@BallerinaFunction(orgName = "wso2",
                   packageName = "redis",
                   functionName = "zRevRangeByScore",
                   receiver = @Receiver(type = TypeKind.STRUCT,
                                        structType = Constants.CALLER_ACTIONS))
public class ZRevRangeByScore extends AbstractRedisAction {

    @Override
    public void execute(Context context) {
        BStruct bConnector = (BStruct) context.getRefArgument(0);
        RedisDataSource redisDataSource = (RedisDataSource) bConnector.getNativeData(Constants.CALLER_ACTIONS);

        String key = context.getStringArgument(0);
        double min = (int) context.getIntArgument(0);
        double max = context.getIntArgument(1);
        BStringArray result = zRevRangeByScore(key, min, max, redisDataSource);
        try {
            context.setReturnValues(result);
        } catch (Throwable e) {
            context.setReturnValues(RedisDataSourceUtils.getRedisConnectorError(context, e));
        }
    }
}