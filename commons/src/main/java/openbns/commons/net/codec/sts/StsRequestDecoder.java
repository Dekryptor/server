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

/**
 * Decodes {@link io.netty.buffer.ByteBuf}s into {@link StsRequest}s and {@link StsContent}s.
 * <p/>
 * <h3>Parameters that prevents excessive memory consumption</h3>
 * <table border="1">
 * <tr>
 * <th>Name</th><th>Meaning</th>
 * </tr>
 * <tr>
 * <td>{@code maxInitialLineLength}</td>
 * <td>The maximum length of the initial line (e.g. {@code "GET / HTTP/1.0"})
 * If the length of the initial line exceeds this value, a
 * {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxHeaderSize}</td>
 * <td>The maximum length of all headers.  If the sum of the length of each
 * header exceeds this value, a {@link TooLongFrameException} will be raised.</td>
 * </tr>
 * <tr>
 * <td>{@code maxChunkSize}</td>
 * <td>The maximum length of the content or each chunk.  If the content length
 * exceeds this value, the transfer encoding of the decoded request will be
 * converted to 'chunked' and the content will be split into multiple
 * {@link StsContent}s.  If the transfer encoding of the HTTP request is
 * 'chunked' already, each chunk will be split into smaller chunks if the
 * length of the chunk exceeds this value.  If you prefer not to handle
 * {@link StsContent}s in your handler, insert {@link HttpObjectAggregator}
 * after this decoder in the {@link io.netty.channel.ChannelPipeline}.</td>
 * </tr>
 * </table>
 */
public class StsRequestDecoder extends HttpObjectDecoder
{

  /**
   * Creates a new instance with the default
   * {@code maxInitialLineLength (4096}}, {@code maxHeaderSize (8192)}, and
   * {@code maxChunkSize (8192)}.
   */
  public StsRequestDecoder()
  {
  }

  /**
   * Creates a new instance with the specified parameters.
   */
  public StsRequestDecoder( int maxInitialLineLength, int maxHeaderSize, int maxChunkSize )
  {
    super( maxInitialLineLength, maxHeaderSize, maxChunkSize, true );
  }

  public StsRequestDecoder( int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders )
  {
    super( maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders );
  }

  @Override
  protected StsMessage createMessage( String[] initialLine ) throws Exception
  {
    return new DefaultStsRequest( StsVersion.valueOf( initialLine[ 2 ] ), StsMethod.valueOf( initialLine[ 0 ] ), initialLine[ 1 ], validateHeaders );
  }

  @Override
  protected StsMessage createInvalidMessage()
  {
    return new DefaultStsRequest( StsVersion.STS_1_0, StsMethod.GET, "/bad-request", validateHeaders );
  }

  @Override
  protected boolean isDecodingRequest()
  {
    return true;
  }
}
