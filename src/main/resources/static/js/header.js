// FIXME 관리자 페이지 접근부터 어드민 권한이 아니면 접근을 못하게 하고 싶은데,
// (제 예상이지만) axios를 통해 전송한 GET /admin 요청의 경우 헤더 정보에 'Authorization'이 들어가지만
// 내부적으로 자원이 존재하는 뷰 이름을 반환하면서 페이지 로딩을 할 때 헤더 정보가 사라지면서 null이 되어서 접근 권한이 없다고 뜨더라구요... ㅠ_ㅠ
// 이 부분은 어떻게 수정할 수 있을까요? 혼자 이것저것 해봤는데 도저히 답이 안 나와서 도움을 요청해봅니다...
// 그래서 임시적으로 인터셉터에서 관리자 메인 페이지 (상품 목록 조회)는 통과하지 않도록 만들어두기는 했어요...!

const addAuthorizationHeader = () => {
  window.location.href = '/admin';

  // FIXME 아래 코드를 적용하게 되면 Unauthorized Exception이 발생하게 돼요...
  // const credentials = localStorage.getItem('credentials');
  // axios.request({
  //   url: `/admin`,
  //   method: 'GET',
  //   headers: {
  //     'Authorization': `Basic ${credentials}`
  //   }
  // }).then((response) => {
  //   window.location.href = '/admin';
  // }).catch((error) => {
  //   console.error(error);
  // });
}
