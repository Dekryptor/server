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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

/**
 * The last {@link StsContent} which has trailing headers.
 */
public interface LastStsContent extends StsContent
{

  /**
   * The 'end of content' marker in chunked encoding.
   */
  LastStsContent EMPTY_LAST_CONTENT = new LastStsContent()
  {

    @Override
    public ByteBuf content()
    {
      return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public LastStsContent copy()
    {
      return EMPTY_LAST_CONTENT;
    }

    @Override
    public LastStsContent duplicate()
    {
      return this;
    }

    @Override
    public StsHeaders trailingHeaders()
    {
      return StsHeaders.EMPTY_HEADERS;
    }

    @Override
    public DecoderResult getDecoderResult()
    {
      return DecoderResult.SUCCESS;
    }

    @Override
    public void setDecoderResult( DecoderResult result )
    {
      throw new UnsupportedOperationException( "read only" );
    }

    @Override
    public int refCnt()
    {
      return 1;
    }

    @Override
    public LastStsContent retain()
    {
      return this;
    }

    @Override
    public LastStsContent retain( int increment )
    {
      return this;
    }

    @Override
    public boolean release()
    {
      return false;
    }

    @Override
    public boolean release( int decrement )
    {
      return false;
    }
  };

  StsHeaders trailingHeaders();

  @Override
  LastStsContent copy();

  @Override
  LastStsContent retain( int increment );

  @Override
  LastStsContent retain();
}
