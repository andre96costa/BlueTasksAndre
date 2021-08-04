import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import AuthService from '../api/AuthService';
import TaskService from '../api/TaskService';
import Alert from './Alert';
import Spinner from './Spinner';

class TaskForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            task: {
                id: 0,
                description: "",
                whenTodo: ""
            },
            redirect : false,
            isAlterar: false,
            alert: null,
            loadding: false,
            saving: false,
        }
        
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
        this.onInputChangeHandler = this.onInputChangeHandler.bind(this);
        
    }
    
    componentDidMount(){
        const editId = this.props.match.params.id;
        if (editId) {
            this.setState({loadding: true});
            TaskService.load(
                parseInt(editId), 
                task => {
                    this.setState({ task: task, loadding: false, isAlterar:true })        
                }, 
                error => {
                    if (error.response) {
                        if(error.response.status === 404){
                            this.setState({ alert: "Tarefa não encontrada", loadding:false});
                        }else{
                            this.setState({alert: `Erro ao carregar dados: ${error.response}`, loadding: false});
                        }
                    }else{
                        this.setState({alert: `Erro na requisição: ${error.message}`, loadding: false})
                    }
                }
            );
        }
    }

    onSubmitHandler(event){
        event.preventDefault();
        this.setState({ saving: true , alert: null})
        TaskService.save(
            this.state.task, 
            () => {
                this.setState({ redirect: true , saving: false });
            },
            (error) => {
                if (error.response) {
                    if(error.response.status === 404){
                        this.setState({ alert: "Tarefa não encontrada", loadding:false});
                    }else{
                        this.setState({alert: `Erro: ${error.response.data.error}`, loadding: false, saving: false});
                    }
                }else{
                    this.setState({alert: `Erro na requisição: ${error.message}`, loadding: false, saving: false})
                }
            }
        );
    }

    onInputChangeHandler(event){
        const field = event.target.name;
        const value = event.target.value;
        this.setState(prevState => ({ task: {...prevState.task, [field]: value}}));
    }

    render() {
        if (!AuthService.isAuthenticated()) {
            return <Redirect to="/login" />
        }

        if (this.state.redirect) {
            return <Redirect to="/" />
        }

        if (this.state.loadding) {
            return <Spinner />
        }
        return (
            <div>
                <h1>Cadastro da tarefa</h1>
                {this.state.alert !== null ? <Alert message={this.state.alert} /> : ""}
                <form onSubmit={this.onSubmitHandler}>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">Descrição</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            name="description"
                            value={this.state.task.description} 
                            placeholder="Digite a descrição" 
                            onChange={this.onInputChangeHandler} 
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="whenTodo" className="form-label">Data</label>
                        <input 
                            type="date" 
                            className="form-control" 
                            name="whenTodo"
                            value={this.state.task.whenTodo} 
                            placeholder="Informe a data" 
                            onChange={this.onInputChangeHandler}     
                        />
                    </div> 
                    <button type="submit" className="btn btn-primary" disabled={this.state.saving}>{(this.state.saving) ? <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> : (this.state.isAlterar) ? "Alterar" : "Cadastrar" }</button>
                    &nbsp;&nbsp;
                    <button type="button" className="btn btn-danger" onClick={() => this.setState({ redirect: true })}>Cancelar</button>
                </form>
            </div>
        );
    }
}

export default TaskForm;    