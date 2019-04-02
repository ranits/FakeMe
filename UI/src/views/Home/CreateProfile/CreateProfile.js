import React, {Fragment} from 'react'
import autobind from "auto-bind";
import Toggle from 'react-toggle'
import {Group, PermIdentity} from '@material-ui/icons/index'
import Button from '@material-ui/core/Button';
import ApiService from '../../../Utils/ApiService'

import "react-toggle/style.css"
import './createProfile.css'

class CreateProfile extends React.Component {
    constructor(props) {
        super(props)
        autobind(this)
        this.state = {
            type: 'group',
            links: ['']
        }
    }

    handleToggle() {
        this.setState(state => ({type: state.type === 'group' ? 'singles' : 'group'}))
    }

    getTypeToggle() {
        const {type} = this.state
        return (
            <Fragment>
                <span>Singles</span>
                <Toggle
                    checked={type === 'group'}
                    icons={{
                        checked: <Group/>,
                        unchecked: <PermIdentity/>,
                    }}
                    onChange={this.handleToggle}
                    className='toggle-background'
                />
                <span>Group</span>
            </Fragment>
        )
    }

    getLinks() {
        const {links} = this.state
        return links.map((link, index) =>
            <div className='link-wrapper' key={`link-${index}`}>
                <span >Link:</span>
                <input
                    type='text'
                    onChange={(event) => this.handleLinksChange(event, index)}
                    value={links[index]}
                />
            </div>
        )
    }

    handleLinksChange(event, index) {
        const {links} = this.state
        const newLinks = [...links]
        newLinks[index] = event.target.value
        this.setState({links: newLinks})
    }

    createProfile() {
        const {closeModal} = this.props
        const {type, links} = this.state
        const filterLinks = links.filter(link => link)
        debugger
        ApiService.apiRequest(ApiService.ROUTES.POST.CREATE_PROFILE, {type, links: filterLinks})
        closeModal(true)

    }

    render() {
        const {closeModal} = this.props
        return (
            <div className='profile-wrapper'>
                <span>Create new profile</span>
                <div className='toggle-wrapper'>
                    {this.getTypeToggle()}
                </div>
                <div>
                    <Button variant="outlined" onClick={() => this.setState(({links}) => ({
                        links: [...links, '']
                    }))}>
                        Add link
                    </Button>
                </div>
                <div className='links-wrapper'>
                    {this.getLinks()}
                </div>
                <div className='cancel-submit-btn'>
                    <Button variant="contained" color="primary" onClick={this.createProfile}>
                        Create
                    </Button>
                    <Button variant="contained" color="secondary" onClick={() => closeModal(false)}>
                        Cancel
                    </Button>
                </div>
            </div>
        )
    }
}

export default CreateProfile