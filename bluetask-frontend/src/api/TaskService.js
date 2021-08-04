import axios from "axios";
import { API_ENDPOINT } from "../constants";
import AuthService from "./AuthService";

class TaskService {

    list(onFetch, onError){
        axios.get(`${API_ENDPOINT}/tasks?sort=whenTodo,asc`, this.buildAuthHeader())
            //.then(response => onFetch(response.data.content))
            .then(response => onFetch(response.data._embedded.tasks))
            .catch(error => onError(error));
    }

    delete(id, onDelete, onError){
        axios.delete(`${API_ENDPOINT}/tasks/${id}`, this.buildAuthHeader())
            //.then(response => onFetch(response.data.content))
            .then(() => onDelete())
            .catch(error => onError(error));
    }

    save(task, onSave, onError){
       if (task.id === 0) {
           axios.post(`${API_ENDPOINT}/tasks`, task, this.buildAuthHeader())
                .then(() => onSave())
                .catch(error => onError(error));
       }
        else{
            axios.put(`${API_ENDPOINT}/tasks/${task.id}`, task, this.buildAuthHeader())
                .then((n) => onSave(n))
                .catch();
        }
    }

    load(id, onLoad, onError){
        axios.get(`${API_ENDPOINT}/tasks/${id}`, this.buildAuthHeader())
            //.then(response => onFetch(response.data.content))
            .then(response => onLoad(response.data))
            .catch(error => onError(error));
    }

    buildAuthHeader(){
        return {
            headers:{
                'Authorization': `Bearer ${AuthService.getJWTToken()}`
            }
        }
    }

}


export default new TaskService();