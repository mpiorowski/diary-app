import React, {Component} from 'react';
import {serviceDiaryAddQuestion, serviceDiaryFindAllLimit} from "../../../../services/diaries/DiariesService";
import moment from "moment";
import NewestList from "./NewestList";
import {openNotification} from "../../../../common/notifications/DiaryNotifications";

class NewestComponent extends Component {

  state = {
    diaries: []
  };

  componentDidMount() {
    let diariesArray = [];
    serviceDiaryFindAllLimit(10).then(diaries => {
      diaries.forEach(diary => {
        diariesArray.push(
            {
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
              userName: diary.diaryAuthor.userName
            }
        )
      });
      this.setState({
        diaries: diariesArray
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
    return (
        <NewestList
            handleSubmit={this.handleSubmit}
            diaries={this.state.diaries}
            userRole={this.props.userRole}
        />
    );
  }
}

export default NewestComponent;
