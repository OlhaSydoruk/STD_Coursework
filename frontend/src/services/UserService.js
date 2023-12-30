import axios from "axios";

const USERS_API_BASE_URL = 'http://localhost:8081/users';

class UserService {
    handleError(error) {
        if (error) {
            this.$router.push({name: 'ErrorPage', params: {errorMessage: error.response.data ?? error.message}});
        }
    }
    getUsers() {
        return axios.get(USERS_API_BASE_URL);
    }
    getUser(id) {
        return axios.get(USERS_API_BASE_URL + `/${id}`).catch(this.handleError);
    }
    insertUser(user) {
        return axios.post(USERS_API_BASE_URL, user).catch(this.handleError);
    }
    updateUser(user) {
        return axios.put(USERS_API_BASE_URL, user).catch(this.handleError);
    }
    deleteUser(id) {
        return axios.delete(USERS_API_BASE_URL + `/${id}`).catch(this.handleError);
    }
    setUserRegistered(value) {
        return axios.put(USERS_API_BASE_URL + "/registration-status", value, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).catch(this.handleError);
        ;
    }

}

export default new UserService()