import React, {Component} from 'react';
import {Col, Dropdown, Icon, Layout, Menu} from "antd";
import avatar from "../img/default-avatar.png";
import './AppHeaderMobile.less';
import {Link, NavLink} from "react-router-dom";
import AppNotifications from "./AppNotifications";

const {Header} = Layout;

class AppHeaderMobile extends Component {

  state = {
    userRole: this.props.currentUser.authorities[0].authority,
  };

  render() {

    const userRole = this.state.userRole;
    const loginMenu = (
        <Menu className={"header-dropdown-menu"}>
          <Menu.Item className={"header-dropdown-menu-item"}>
            <NavLink to="/contact">
              <Icon type="phone"/> Kontakt
            </NavLink>
          </Menu.Item>
          <Menu.Divider className={"header-dropdown-divider"}/>
          <Menu.Item className={"header-dropdown-menu-item"} onClick={this.props.logout}>
            <NavLink to={'/login'}>
              <Icon type="logout"/> Wyloguj się
            </NavLink>
          </Menu.Item>
        </Menu>
    );

    return (
        <Header className={'header-main-mobile'} style={{zIndex: "2"}}>
          <div className={"header-menu-mobile"}>
            <Col span={11}>
              <div className="header-submenu-icon-mobile" key={1}>
                <Link to={"/diary/today"}>
                  <Icon type="book" className={"icon"}/>
                  <h2>Dzienniczek</h2>
                </Link>
              </div>
            </Col>
            <Col span={7}>
              <div className={"header-submenu-mobile"} key={2}>
                <Link to={"/diary/history"}>
                  <h2>Historia</h2>
                </Link>
              </div>
            </Col>
            <Col span={3}>
              <div className={"header-notification-mobile"} >
                <AppNotifications
                    userRole={userRole}
                />
              </div>
            </Col>
            <Col span={3}>
              <div className={"header-submenu-mobile"} key={3}>
                <Dropdown overlay={loginMenu}
                          placement="bottomRight"
                          trigger={["click"]}>
                  <div style={{height:'100%'}}>
                    <img src={avatar} alt="" width={22}/>
                  </div>
                </Dropdown>
              </div>
            </Col>
          </div>
        </Header>
    );
  }
}

export default AppHeaderMobile;
