export default function (error, router) {
    console.log('Error stack:', error.stack);
    const errorCode = error.code || error.status || 'Неизвестный код ошибки';
    router.push({
        path: '/error',
        query: {
            errorStack: error.stack,
            errorCode: errorCode
        }
    });
}
