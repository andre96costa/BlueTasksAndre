import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import TaskService from '../api/TaskService';

class TaskListTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            tasks: [],
            editId: 0
        }

        this.onDeleteHandler = this.onDeleteHandler.bind(this);
        this.onStatusChangeHandler = this.onStatusChangeHandler.bind(this);
        this.onEditHandler = this.onEditHandler.bind(this);
    }

    componentDidMount() {
        this.listTasks();
    }

    listTasks() {
        this.setState({ tasks: TaskService.list() });
    }

    onDeleteHandler(id){
        if (window.confirm("Deseja excluir essa tarefa?")) {
            TaskService.delete(id);
            this.listTasks();
            toast.success("Tarefa excluida", { position: toast.POSITION.BOTTOM_LEFT});   
        }
    }

    onEditHandler(id){
        this.setState({editId : id});
    }

    onStatusChangeHandler(task){
        task.done = !task.done;
        TaskService.save(task);
        this.listTasks();
    }


    render() {
        if (this.state.editId > 0) {
            return <Redirect to={`/form/${this.state.editId}`} />
        }
        return (
            <>
                <table className="table table-striped">
                    <TableHeader />
                {this.state.tasks.length > 0 ?
                        <TableBody  tasks={this.state.tasks} 
                            onDelete={this.onDeleteHandler} 
                            onStatusChange={this.onStatusChangeHandler}
                            onEdit={this.onEditHandler} 
                        />
                        :
                        <EmptyTableBody/>
                }
                </table> 
                <ToastContainer autoClose={1500}/>
            </>
        );
    }
}

const TableHeader = () => {
    return (
        
        <thead className="table-dark">
                <tr>
                <th scope="col">Status</th>
                <th scope="col">Descrição</th>
                <th scope="col">Data</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
       
    );
}


const TableBody = (props) => {
    return (
        <tbody>
            {
                props.tasks.map(task =>
                    <tr key={task.id}>
                        <td><input type="checkbox" checked={(task.done)} onChange={(event) => props.onStatusChange(task)} /></td>
                        <td>{task.done ? <s>{task.description}</s> : task.description}</td>
                        <td>{task.done ? <s>{task.whenTodo}</s> : task.whenTodo}</td>
                        <td>
                            <input type="button" value="Editar" className="btn btn-primary" onClick={() => props.onEdit(task.id)} />
                            &nbsp;
                            <input type="button" value="Excluir" className="btn btn-danger" onClick={() => props.onDelete(task.id)} />
                        </td>
                    </tr>
                )
            }
        </tbody>
    );
}

const EmptyTableBody = (props) => {
    return (
        <tbody>
            <tr>
                <td colSpan="4">Sem tarefas cadastradas no momento</td>
            </tr>
        </tbody>
    );
}
export default TaskListTable;

