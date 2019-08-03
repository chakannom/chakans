export const SIDEBAR_MENU = (blogId?: number) => {
  let menuItems: any[];
  if (blogId === undefined) {
    menuItems = [
      {
        navigation: {
          routerLink: ['/blog.cb'],
          fragment: 'welcome'
        },
        icon: ['far', 'smile'],
        name: {
          label: 'Welcome',
          translateKey: 'blog.sidebar.menu.welcome'
        }
      },
      {
        name: {
          label: 'Settings',
          translateKey: 'blog.sidebar.menu.settings'
        },
        subItems: [
          {
            navigation: {
              routerLink: ['/blog.cb'],
              fragment: 'usersettings'
            },
            icon: ['far', 'user'],
            name: {
              label: 'User',
              translateKey: 'blog.sidebar.sub-menu.settings.user'
            }
          }
        ]
      }
    ];
  } else {
    menuItems = [
      {
        navigation: {
          routerLink: ['/blog.cb'],
          queryParams: { blogId },
          fragment: 'viewblog'
        },
        icon: ['fa', 'tv'],
        name: {
          label: 'View Blog',
          translateKey: 'blog.sidebar.menu.viewblog'
        }
      },
      {
        navigation: {
          routerLink: ['/blog.cb'],
          queryParams: { blogId },
          fragment: 'stats'
        },
        icon: ['fa', 'chart-bar'],
        name: {
          label: 'Stats',
          translateKey: 'blog.sidebar.menu.stats'
        }
      },
      {
        name: {
          label: 'Manage',
          translateKey: 'blog.sidebar.menu.manage'
        },
        subItems: [
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'pages/published'
            },
            icon: ['far', 'file'],
            name: {
              label: 'Pages',
              translateKey: 'blog.sidebar.sub-menu.manage.pages'
            },
            ext: {
              navigation: {
                routerLink: ['/blog.cb'],
                queryParams: { blogId },
                fragment: 'editor/type=page'
              },
              icon: ['fa', 'plus']
            }
          },
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'posts/published'
            },
            icon: ['far', 'file-alt'],
            name: {
              label: 'Posts',
              translateKey: 'blog.sidebar.sub-menu.manage.posts'
            },
            ext: {
              navigation: {
                routerLink: ['/blog.cb'],
                queryParams: { blogId },
                fragment: 'editor/type=post'
              },
              icon: ['fa', 'plus']
            }
          },
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'comments/published'
            },
            icon: ['far', 'comment'],
            name: {
              label: 'Comments',
              translateKey: 'blog.sidebar.sub-menu.manage.comments'
            }
          }
        ]
      },
      {
        name: {
          label: 'Settings',
          translateKey: 'blog.sidebar.menu.settings'
        },
        subItems: [
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'basicsettings'
            },
            icon: ['fa', 'cog'],
            name: {
              label: 'Basic',
              translateKey: 'blog.sidebar.sub-menu.settings.basic'
            }
          },
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'othersettings'
            },
            icon: ['fa', 'cogs'],
            name: {
              label: 'Other',
              translateKey: 'blog.sidebar.sub-menu.settings.other'
            }
          },
          {
            navigation: {
              routerLink: ['/blog.cb'],
              queryParams: { blogId },
              fragment: 'usersettings'
            },
            icon: ['far', 'user'],
            name: {
              label: 'User',
              translateKey: 'blog.sidebar.sub-menu.settings.user'
            }
          }
        ]
      }
    ];
  }
  return menuItems;
};
