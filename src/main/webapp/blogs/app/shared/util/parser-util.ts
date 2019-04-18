export const parseFragment = (fragment: string): any => {
    const params = {};
    const pairs = fragment.split(';');
    for (let i = 0; i < pairs.length; i++) {
        const pair = pairs[i].split('=');
        params[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1] || '');
    }
    return params;
};
