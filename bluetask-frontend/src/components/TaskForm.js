import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import TaskService from '../api/TaskService';

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
        }
        
        this.onSubmitHandler = this.onSubmitHandler.bind(this);
        this.onInputChangeHandler = this.onInputChangeHandler.bind(this);
        
    }
    
    componentDidMount(){
        const editId = this.props.match.params.id;
        if (editId) {
            const task = TaskService.load(parseInt(editId));
            this.setState({isAlterar: true});
            this.setState({ task: task });
        }
    }

    onSubmitHandler(event){
        event.preventDefault();
        TaskService.save(this.state.task);
        this.setState({redirect: true});
    }

    onInputChangeHandler(event){
        const field = event.target.name;
        const value = event.target.value;
        this.setState(prevState => ({ task: {...prevState.task, [field]: value}}));
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to="/" />
        }
        return (
            <div>
                <h1>Cadastro da tarefa</h1>
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
                    <button type="submit" className="btn btn-primary">{(this.state.isAlterar) ? "Alterar" : "Cadastrar"}</button>
                    &nbsp;&nbsp;
                    <button type="button" className="btn btn-danger" onClick={() => this.setState({ redirect: true })}>Cancelar</button>
                </form>
            </div>
        );
    }
}

export default TaskForm;    