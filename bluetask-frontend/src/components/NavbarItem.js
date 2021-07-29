import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class NavbarItem extends Component {
    render() {
        return (
            <div>
                <Link to={this.props.item.href} 
                    onClick={e => this.props.onClick(this.props.item)} 
                    className={`nav-item nav-link ${(this.props.item.active) ? "active" : ""}`} >
                        {this.props.item.name}
                </Link>
            </div>
        );
    }
}

export default NavbarItem;