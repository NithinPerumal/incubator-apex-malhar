/*
 * Copyright (c) 2013 DataTorrent, Inc. ALL Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datatorrent.contrib.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>RedisNumberAggregateMapOutputOperator class.</p>
 * This class provides output adapter that takes a map of key type K and value type V, and if
 * V is a map, it will increment the values in the map in redis as hash, otherwise, it will increment the value
 * as is.
 *
 * @param <K> The key type
 * @param <V> The value type
 * @since 0.3.2
 */
public class RedisNumberAggregateMapOutputOperator<K, V> extends AbstractRedisAggregateOutputOperator<Map<K, V>>
{
  private Map<Object, Object> dataMap = new HashMap<Object, Object>();
  private NumberAggregation<K,V> numberAggregation = new NumberAggregation<K,V>(store, dataMap);

  @Override
  public void processTuple(Map<K, V> t)
  {
    for (Map.Entry<K, V> entry : t.entrySet()) {
      numberAggregation.process(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void storeAggregate()
  {
    numberAggregation.storeAggregate();
  }

}
