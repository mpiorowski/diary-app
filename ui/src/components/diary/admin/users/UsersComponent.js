import React, {Component} from 'react';
import {serviceDiaryAddQuestion, serviceFindOrderByDate} from "../../../../services/diaries/DiariesService";
import {serviceUsersFindAll} from "../../../../services/users/UsersServices";
import moment from "moment";
import UsersList from "./UsersList";
import {openNotification} from "../../../../common/notifications/DiaryNotifications";

class UsersComponent extends Component {

  state = {
    diaries: {},
    diary: {
      uid: '',
      diaryAuthor: '',
      diaryDate: '',
      diaryDescription: '',
      diaryLike: '',
      diaryAmount: '',
      question: '',
      createdAt: '',
      updatedAt: '',
    },
    usersName: {},
  };

  componentDidMount() {
    let id = 0;
    let diariesArray = {};
    let usersName = new Map();

    serviceUsersFindAll().then(users => {
      users.forEach(user => {
        diariesArray = {
          ...diariesArray,
          [user.uid]: {}
        };
        usersName.set(user.uid, user.userName);
      });
      serviceFindOrderByDate().then(diaries => {
        console.log('response', diaries);
        diaries.forEach(diary => {
          diariesArray[diary.diaryAuthor.uid] = {
            ...diariesArray[diary.diaryAuthor.uid],
            [diary.diaryDate]: {
              ...diariesArray[diary.diaryAuthor.uid][diary.diaryDate],
              [id]: {
                diaryUid: diary.uid,
                diaryType: diary.diaryType,
                diaryDescription: diary.diaryDescription,
                diaryLike: diary.diaryLike,
                diaryAmount: diary.diaryAmount,
                diaryDate: diary.diaryDate,
                question: diary.question,
                answer: diary.answer,
                createdAt: moment(diary.createdAt).format("YYYY-MM-DD HH:mm:ss"),
                updatedAt: moment(diary.updatedAt).format("YYYY-MM-DD HH:mm:ss"),
              }
            }
          };

          id++;
        });

        this.setState({
          diaries: diariesArray,
          usersName: usersName,
          loading: false,
        });
      })
    });
  }

  handleSubmit = (e, form, initialKeys) => {
    e.preventDefault();
    form.validateFields((err, diary) => {
      console.log(diary);
      if (!err) {
        const data = {question: diary.question};
        serviceDiaryAddQuestion(diary.diaryUid, data).then(response => {
          if (response) {
            openNotification('success');
          } else {
            openNotification('error');
          }
        }).catch(error => {
          console.log(error);
          openNotification('error');
        })
      }
    });
  };

  render() {
    let {diaries, usersName} = this.state;
    return (
        <UsersList
            diaries={diaries}
            usersName={usersName}
            handleSubmit={this.handleSubmit}
            location={this.props.location}
            userRole={this.props.userRole}
        />
    );
  }
}

export default UsersComponent;
