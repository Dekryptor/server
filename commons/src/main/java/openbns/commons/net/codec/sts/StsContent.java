/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package openbns.commons.net.codec.sts;

import io.netty.buffer.ByteBufHolder;

/**
 * An HTTP chunk which is used for HTTP chunked transfer-encoding.
 * {@link HttpObjectDecoder} generates {@link StsContent} after
 * {@link StsMessage} when the content is large or the encoding of the content
 * is 'chunked.  If you prefer not to receive {@link StsContent} in your handler,
 * place {@link HttpObjectAggregator} after {@link HttpObjectDecoder} in the
 * {@link io.netty.channel.ChannelPipeline}.
 */
public interface StsContent extends StsObject, ByteBufHolder
{
  @Override
  StsContent copy();

  @Override
  StsContent duplicate();

  @Override
  StsContent retain();

  @Override
  StsContent retain( int increment );
}
