import React, {Component} from 'react';
import {Badge, Dropdown, Icon, Menu, Popconfirm} from "antd";
import {NavLink} from "react-router-dom";
import {
  serviceNotificationDelete,
  serviceNotificationFindAll,
  serviceNotificationRead
} from "../services/notifications/NotificationsService";

import './AppNotifications.less';

class AppNotifications extends Component {

  state = {
    themeLoading: false,
    notificationKeys: [],
    activeNotification: [],
    notifications: [{
      notificationValues: [],
      notificationType: '',
      notificationAuthor: {},
      isRead: false
    }],
    notificationVisible: false,
    userRole: this.props.userRole
  };

  componentDidMount() {
    serviceNotificationFindAll().then(notifications => {
      if (!notifications || !Array.isArray(notifications)) {
        notifications = [];
      }
      console.log('notifications', notifications);
      // let notificationKeys = [...Array(notifications.length).keys()];
      let notificationKeys = [];
      let activeNotification = [];
      let i = 0;
      notifications.forEach(notification => {
        if (!notification.isRead) {
          activeNotification.push(i);
        }
        notificationKeys.push(i);
        i++;
      });
      this.setState({
        notifications: notifications,
        notificationKeys: notificationKeys,
        activeNotification: activeNotification,
      })
    })
  }


  handleVisibleChange = flag => {
    this.setState({notificationVisible: flag});
  };

  handleMenuClick = e => {
    // this.setState({notificationVisible: false});
  };

  readNotification = (e, k, uid) => {
    serviceNotificationRead(uid).then(() =>
        this.setState({
          activeNotification: this.state.activeNotification.filter(key => key !== k)
        })
    );
    e.target.style = "font-weight: 200";
  };

  deleteNotification = (k, uid) => {
    serviceNotificationDelete(uid).then(response => {
      if (response) {
        this.setState({
          notificationKeys: this.state.notificationKeys.filter(key => key !== k),
          activeNotification: this.state.activeNotification.filter(key => key !== k)
        });
      }
    })
  };

  deleteAllNotification = (keys) => {
    const notifications = this.state.notifications;
    const promiseArray = [];
    console.log(notifications);
    keys.forEach(key => {
          promiseArray.push(serviceNotificationDelete(notifications[key].uid));
        }
    );
    Promise.all(promiseArray).then(() => {
          this.setState({
            notificationKeys: [],
            activeNotification: []
          });
        }
    )
  };

  render() {

    const isMobile = window.innerWidth <= 576;

    const notifications = this.state.notifications;
    let notificationKeys = this.state.notificationKeys;
    let activeNotification = this.state.activeNotification;

    let notificationMenuItems;

    if (notificationKeys.length > 0) {
      notificationMenuItems = notificationKeys.map((key, index, arr) => {
        const uid = notifications[key].uid;
        const type = notifications[key].notificationType;

        const diaryUid = notifications[key].notificationValues[0];
        const date = notifications[key].notificationValues[1];
        const diaryAuthor = notifications[key].notificationAuthor.uid;
        const icon = <Icon type="right"/>;

        let notificationUrl;
        let notificationMessage;

        if (type === 'diary-answer') {
          notificationMessage = 'Odpowidziano na pytanie z dnia ' + date;
          notificationUrl = `/diary/users#${diaryAuthor}#${date}#${diaryUid}`;
        } else {
          notificationMessage = isMobile
          ? 'Pytanie do wpisu [' + date + ']'
              : 'Zadano pytanie do wpisu z dnia ' + date;
          notificationUrl = `/diary/history#${date}#${diaryUid}`;
        }

        return [
          <Menu.Item className={"header-dropdown-notification-item"}>
            <div className={"header-dropdown-notification-div-left"}>
              <NavLink to={notificationUrl}>
                {activeNotification.includes(key) !== true
                    ? <span style={{fontWeight: 200}}>{notificationMessage} {icon}</span>
                    :
                    <span style={{fontWeight: 600}} onClick={(e) => this.readNotification(e, key, uid)}>
                      {notificationMessage} {icon}
                    </span>
                }
              </NavLink>
            </div>
            <Popconfirm
                className={'header-notification-delete'}
                placement="bottomRight"
                title={"Czy na pewno chcesz usunąć powiadomienie?"}
                onConfirm={() => this.deleteNotification(key, uid)}
                okText="Tak"
                cancelText="Nie">
              <div className={"header-dropdown-notification-div-right"}>
                <Icon type="delete"/>
              </div>
            </Popconfirm>

          </Menu.Item>,
          arr.length - 1 !== index ? <Menu.Divider className={"header-dropdown-divider"}/> : ''
        ]
      });
      notificationMenuItems.push(
          [
            <Menu.Divider className={"header-dropdown-divider"}/>,
            <Menu.Item className={"header-dropdown-notification-item"} key={1}>
              <Popconfirm
                  className={'header-notification-delete'}
                  placement="bottomRight"
                  title={"Czy na pewno chcesz usunąć wszystkie powiadomienie?"}
                  onConfirm={() => this.deleteAllNotification(notificationKeys)}
                  okText="Tak"
                  cancelText="Nie">
                <div className={"header-dropdown-notification-none delete"}>
                  Usuń wszystkie powiadomienia
                </div>
              </Popconfirm>

            </Menu.Item>
          ]
      );
    } else {
      notificationMenuItems = [
        <Menu.Item className={"header-dropdown-notification-item"} key={1}>
          <div className={"header-dropdown-notification-none"}>
            Brak powiadomień
          </div>
        </Menu.Item>
      ]
    }

    const notificationMenu =
        <Menu className={"header-dropdown-notification"} onClick={this.handleMenuClick}>
          {notificationMenuItems}
        </Menu>;

    return (
        <Dropdown
            overlay={notificationMenu}
            visible={this.state.notificationVisible}
            onVisibleChange={this.handleVisibleChange}
            placement="bottomRight"
            trigger={["click"]}
        >
              <div className={"header-dropdown"}>
                <Badge
                    className={"header-notification"}
                    count={activeNotification.length}
                    style={{boxShadow: 'none'}}
                >
                  <Icon type="bell"/>
                </Badge>
              </div>
        </Dropdown>
    );
  }
}

export default AppNotifications;
