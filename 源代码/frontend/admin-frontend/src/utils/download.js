// 文件路径: new_yxq/front/houtai/src/utils/download.js

/**
 * 通用下载方法
 * @param {string} url 文件下载地址
 * @param {string} filename 下载后显示的文件名
 */
export function downloadFile(url, filename) {
    const link = document.createElement('a');
    link.style.display = 'none';
    link.href = url;

    if (filename) {
        link.download = filename;
    }

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}