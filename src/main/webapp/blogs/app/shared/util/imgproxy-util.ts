// imgproxy-signature
import * as crypto from 'crypto';

import { IMGPROXY_URL, IMGPROXY_KEY, IMGPROXY_SALT } from '../../app.constants';

export const createImgproxySignatureUrl = (
    resizing_type: string,
    width: number,
    height: number,
    gravity: string,
    enlarge: number,
    non_encoding_url: string,
    extension: string
): string => {
    const urlSafeBase64 = string =>
        new Buffer(string)
            .toString('base64')
            .replace(/=/g, '')
            .replace(/\+/g, '-')
            .replace(/\//g, '_');
    const hexDecode = hex => Buffer.from(hex, 'hex');

    const encoded_url = urlSafeBase64(non_encoding_url);
    const path = `/${resizing_type}/${width}/${height}/${gravity}/${enlarge}/${encoded_url}.${extension}`;

    const hmac = crypto.createHmac('sha256', hexDecode(IMGPROXY_KEY));
    hmac.update(hexDecode(IMGPROXY_SALT));
    hmac.update(path);

    const signature = urlSafeBase64(hmac.digest());

    return `${IMGPROXY_URL}/${signature}${path}`;
};
