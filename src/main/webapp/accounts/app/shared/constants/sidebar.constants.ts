export const SIDEBAR_MENU = () => {
  const menuItems: any[] = [
    {
      navigation: {
        routerLink: ['/my']
      },
      icon: ['fa', 'home'],
      name: {
        label: 'Home'
      }
    },
    {
      navigation: {
        routerLink: ['/my/personal-info']
      },
      icon: ['fa', 'id-card'],
      name: {
        label: 'Personal info'
      }
    },
    {
      navigation: {
        routerLink: ['/my/security']
      },
      icon: ['fa', 'lock'],
      name: {
        label: 'Security'
      }
    }
  ];
  return menuItems;
};
