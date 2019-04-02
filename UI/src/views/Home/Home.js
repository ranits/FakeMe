import React, {Fragment} from 'react'
import Modal from 'react-modal'
import Button from '@material-ui/core/Button';
import autobind from 'auto-bind'
import CreateProfile from './CreateProfile/CreateProfile'
import ProfilePage from './ProfilePage/profilePage'
import Logo from '../../images/logo.png'
import Loading from './Loading/Loading'
import io from 'socket.io-client'
import ApiService from '../../Utils/ApiService'

import './home.css'

const customStyles = {
    content: {
        height: '400px',
        width: '350px',
        top: '40%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)'
    }
};

class Home extends React.Component {

    constructor(props) {
        super(props)
        autobind(this)
        // const socket = io('http://localhost');
        // socket.on('status', (data) => {
        //     if (data === 'done') {
        //         this.endLoading()
        //     } else {
        //         this.setState({status: data})
        //     }
        // })
        this.state = {
            modalIsOpenCreate: false,
            modalIsOpenProfile: false,
            isLoading: false,
            currentProfile: {},
            profiles: [],
            status: ''
        }
    }

    componentDidMount() {
        this.getProfiles()
    }

    openProfile(profile = {}) {
        this.setState({modalIsOpenProfile: true, currentProfile: profile})
    }

    openModal(modal) {
        this.setState({[modal]: true});
    }

    getStatus() {
        const interval = setInterval(async () => {
            const status = await ApiService.ROUTES.GET.GET_STATUS()
            if (status === 'done') {
                clearInterval(interval)
            } else {
                this.setState({status})
            }
        }, 3000)
    }

    closeModal(modal, load) {
        if (load) {
            this.setState({[modal]: false, isLoading: true});
            this.getStatus()
        } else {
            this.setState({[modal]: false});
        }
    }

    async endLoading() {
        this.setState({isLoading: false});
        this.getProfiles()
    }

    createProfiles() {
        const {profiles} = this.state
        return profiles.map((profile, index) => (
            <div key={`image-${index}`} className='image-wrapper' onClick={() => this.openProfile(profile)}>
                <img src={`http://localhost:8000/${profile.imageUrl}`} alt=''/>
                <span className='img__description'> {profile.properties.name}</span>
            </div>
        ))
    }

    async getProfiles() {
        const profiles = await ApiService.apiRequest(ApiService.ROUTES.GET.GET_PROFILE)
        this.setState({profiles})
    }

    render() {
        const {modalIsOpenCreate, modalIsOpenProfile, currentProfile, isLoading, status} = this.state
        return (
            <Fragment>
                <div className='home-wrapper'>
                    <div className='home-head'>
                        <div className='logo'>
                            <img src={Logo} alt=''/>
                            <span> FAKE-AVATAR </span>
                        </div>
                        <div className='create-wrapper'>
                            <Button variant="contained" color="primary"
                                    onClick={() => this.openModal('modalIsOpenCreate')}>
                                Create Profile
                            </Button>
                        </div>
                    </div>
                    <div className='home-profiles'>
                        <div className='images-wrapper'>
                            {this.createProfiles()}
                        </div>
                    </div>
                </div>

                <Modal
                    closeTimeoutMS={200}
                    isOpen={modalIsOpenCreate}
                    ariaHideApp={false}
                    style={customStyles}
                >
                    <CreateProfile
                        closeModal={(load) => this.closeModal('modalIsOpenCreate', load)}
                    />
                </Modal>
                <Modal
                    closeTimeoutMS={200}
                    isOpen={modalIsOpenProfile}
                    ariaHideApp={false}
                    style={customStyles}
                >
                    <ProfilePage
                        closeModal={() => this.closeModal('modalIsOpenProfile')}
                        profile={currentProfile}
                    />
                </Modal>
                <Modal
                    closeTimeoutMS={200}
                    isOpen={isLoading}
                    ariaHideApp={false}
                    style={customStyles}
                >
                    <Loading
                        status={status}
                        endLoading={this.endLoading}
                    />
                </Modal>
            </Fragment>
        )
    }
}

export default Home