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

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * A combination of {@link StsRequestDecoder} and {@link StsResponseEncoder}
 * which enables easier server side HTTP implementation.
 */
public final class StsServerCodec extends CombinedChannelDuplexHandler<StsRequestDecoder, StsResponseEncoder>
{

  /**
   * Creates a new instance with the default decoder options
   * ({@code maxInitialLineLength (4096}}, {@code maxHeaderSize (8192)}, and
   * {@code maxChunkSize (8192)}).
   */
  public StsServerCodec()
  {
    this( 4096, 8192, 8192 );
  }

  /**
   * Creates a new instance with the specified decoder options.
   */
  public StsServerCodec( int maxInitialLineLength, int maxHeaderSize, int maxChunkSize )
  {
    super( new StsRequestDecoder( maxInitialLineLength, maxHeaderSize, maxChunkSize ), new StsResponseEncoder() );
  }

  /**
   * Creates a new instance with the specified decoder options.
   */
  public StsServerCodec( int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders )
  {
    super( new StsRequestDecoder( maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders ), new StsResponseEncoder() );
  }
}
