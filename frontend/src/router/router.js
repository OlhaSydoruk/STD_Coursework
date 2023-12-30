import {createRouter, createWebHistory} from 'vue-router/dist/vue-router.esm-bundler'
import LoginPage from "@/pages/LoginPage.vue";
import AuthorizationPage from "@/pages/AuthorizationPage.vue";
import InfoPage from "@/pages/InfoPage.vue";
import ErrorPage from "@/pages/ErrorPage.vue";

const routes = [
    {
        path: '/',
        name: LoginPage,
        component: LoginPage
    },
    {
        path: '/authorization',
        name: AuthorizationPage,
        component: AuthorizationPage
    },
    {
        path: '/infopage',
        name: InfoPage,
        component: InfoPage
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'ErrorPage',
        component: ErrorPage,
    }
]
export default () => createRouter({
    history: createWebHistory(),
    routes: routes
})