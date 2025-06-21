import http from '../service/Request'

export default {
    /** 上传图片 */
    UploadImage(file) {
        const formData = new FormData();
        formData.append('file', file);

        return http.post('/api/upload/image', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }
}